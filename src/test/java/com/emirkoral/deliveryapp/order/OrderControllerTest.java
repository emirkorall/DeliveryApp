package com.emirkoral.deliveryapp.order;

import com.emirkoral.deliveryapp.auth.dto.AuthenticationRequest;
import com.emirkoral.deliveryapp.auth.dto.AuthenticationResponse;
import com.emirkoral.deliveryapp.order.dto.OrderRequest;
import com.emirkoral.deliveryapp.restaurant.Restaurant;
import com.emirkoral.deliveryapp.restaurant.RestaurantRepository;
import com.emirkoral.deliveryapp.user.User;
import com.emirkoral.deliveryapp.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "jwt.secret=dGVzdC1zZWNyZXQta2V5LWZvci10ZXN0aW5nLW9ubHk=",
    "jwt.expiration=86400000"
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String accessToken;
    private User user;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() throws Exception {
        user = new User();
        user.setEmail("test@test.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRole(User.UserRole.RESTAURANT_OWNER);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setIsActive(true);
        user = userRepository.save(user);

        restaurant = new Restaurant();
        restaurant.setAddress("Test Address");
        restaurant.setName("Test Restaurant");
        restaurant.setOwner(user);
        restaurant = restaurantRepository.save(restaurant);


        AuthenticationRequest authRequest = new AuthenticationRequest("test@test.com", "password");

        MvcResult result = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        AuthenticationResponse authResponse = objectMapper.readValue(content, AuthenticationResponse.class);
        accessToken = authResponse.accessToken();
    }

    @Test
    void whenCreateOrderWithValidToken_thenCreateOrder() throws Exception {
        OrderRequest orderRequest = new OrderRequest(user.getId(), restaurant.getId(), List.of(), "No onions");

        mockMvc.perform(post("/api/orders")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
} 