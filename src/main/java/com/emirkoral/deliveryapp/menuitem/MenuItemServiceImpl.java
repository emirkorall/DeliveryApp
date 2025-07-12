package com.emirkoral.deliveryapp.menuitem;


import com.emirkoral.deliveryapp.exception.ResourceNotFoundException;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemRequest;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemResponse;
import com.emirkoral.deliveryapp.menuitem.mapper.MenuItemMapper;
import com.emirkoral.deliveryapp.restaurant.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;
    private final RestaurantRepository restaurantRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, MenuItemMapper menuItemMapper, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<MenuItemResponse> findAllMenuItems() {
        return menuItemRepository.findAll().stream()
                .map(menuItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponse findMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        return menuItemMapper.toResponse(menuItem);
    }

    @Override
    public List<MenuItemResponse> findByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(menuItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuItemResponse> findByIsAvailable(boolean isAvailable) {
        return menuItemRepository.findByIsAvailable(isAvailable).stream()
                .map(menuItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItemResponse saveMenuItem(MenuItemRequest request) {
        MenuItem menuItem = buildMenuItemFromRequest(request);
        MenuItem saved = menuItemRepository.save(menuItem);
        return menuItemMapper.toResponse(saved);
    }

    @Override
    public MenuItemResponse updateMenuItem(Long id, MenuItemRequest request) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        menuItemMapper.updateEntityFromRequest(request, menuItem);
        menuItem.setRestaurant(restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.restaurantId())));
        MenuItem saved = menuItemRepository.save(menuItem);
        return menuItemMapper.toResponse(saved);
    }

    @Override
    public MenuItemResponse deleteMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + id));
        menuItemRepository.deleteById(id);
        return menuItemMapper.toResponse(menuItem);

    }

    @Override
    public List<MenuItemResponse> searchMenuItems(Long restaurantId, Boolean isAvailable, String name, Double minPrice, Double maxPrice) {
        return menuItemRepository.findAll().stream()
                .filter(mi -> restaurantId == null || (mi.getRestaurant() != null && mi.getRestaurant().getId().equals(restaurantId)))
                .filter(mi -> isAvailable == null || mi.getIsAvailable() == isAvailable)
                .filter(mi -> name == null || (mi.getName() != null && mi.getName().toLowerCase().contains(name.toLowerCase())))
                .filter(mi -> minPrice == null || (mi.getPrice() != null && mi.getPrice().compareTo(BigDecimal.valueOf(minPrice)) >= 0))
                .filter(mi -> maxPrice == null || (mi.getPrice() != null && mi.getPrice().compareTo(BigDecimal.valueOf(maxPrice)) <= 0))
                .map(menuItemMapper::toResponse)
                .collect(Collectors.toList());

    }

    private MenuItem buildMenuItemFromRequest(MenuItemRequest request) {
        MenuItem menuItem = menuItemMapper.toEntity(request);
        menuItem.setRestaurant(restaurantRepository.findById(request.restaurantId())
                .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id: " + request.restaurantId())));
        return menuItem;
    }
}
