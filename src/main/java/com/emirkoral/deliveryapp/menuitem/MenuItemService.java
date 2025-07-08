package com.emirkoral.deliveryapp.menuitem;

import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    List<MenuItem> findAll();

    Optional<MenuItem> findById(Long id);

    List<MenuItem> findByRestaurantId(Long restaurantId);

    List<MenuItem> findByIsAvailable(boolean isAvailable);

    MenuItem save(MenuItem menuItem);

    void deleteById(Long id);
}
