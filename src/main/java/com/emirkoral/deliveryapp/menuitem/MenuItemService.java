package com.emirkoral.deliveryapp.menuitem;

import com.emirkoral.deliveryapp.menuitem.dto.MenuItemRequest;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemResponse;

import java.util.List;


public interface MenuItemService {
    List<MenuItemResponse> findAllMenuItems();

    MenuItemResponse findMenuItemById(Long id);

    List<MenuItemResponse> findByRestaurantId(Long restaurantId);

    List<MenuItemResponse> findByIsAvailable(boolean isAvailable);

    MenuItemResponse saveMenuItem(MenuItemRequest request);

    MenuItemResponse updateMenuItem(Long id, MenuItemRequest request);

    MenuItemResponse deleteMenuItemById(Long id);

    List<MenuItemResponse> searchMenuItems(Long restaurantId, Boolean isAvailable, String name, Double minPrice, Double maxPrice);
}
