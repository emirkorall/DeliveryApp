package com.emirkoral.deliveryapp.order;

import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.order.dto.OrderRequest;
import com.emirkoral.deliveryapp.order.mapper.OrderMapper;
import com.emirkoral.deliveryapp.restaurant.Restaurant;
import com.emirkoral.deliveryapp.restaurant.RestaurantRepository;
import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.emirkoral.deliveryapp.util.AuthorizationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private OrderMapper orderMapper;

    private MockedStatic<AuthorizationUtil> authorizationUtilMockedStatic;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authorizationUtilMockedStatic = Mockito.mockStatic(AuthorizationUtil.class);
        orderService = new OrderServiceImpl(orderRepository, orderMapper, userRepository, restaurantRepository, null);
    }

    @AfterEach
    void tearDown() {
        authorizationUtilMockedStatic.close();
    }

    @Test
    void whenSaveOrderWithValidRequest_thenSaveAndReturnOrder() {
        OrderRequest orderRequest = new OrderRequest(1L, 1L, List.of(), null);
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        authorizationUtilMockedStatic.when(() -> AuthorizationUtil.check(any(), any())).then(invocation -> null);
        when(orderMapper.toEntity(any(OrderRequest.class))).thenReturn(new Order());
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        orderService.saveOrder(orderRequest);
    }

    @Test
    void whenSaveOrderWithInvalidCustomer_thenThrowResourceNotFoundException() {
        OrderRequest orderRequest = new OrderRequest(1L, 1L, List.of(), null);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.saveOrder(orderRequest);
        });
    }
} 