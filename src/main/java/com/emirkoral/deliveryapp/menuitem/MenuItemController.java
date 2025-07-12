package com.emirkoral.deliveryapp.menuitem;


import com.emirkoral.deliveryapp.menuitem.dto.MenuItemRequest;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping

    public ResponseEntity<List<MenuItemResponse>> getAllMenuItems() {
        return ResponseEntity.ok(menuItemService.findAllMenuItems());
    }

    @GetMapping("/{id}")

    public ResponseEntity<MenuItemResponse> getMenuItemById(@PathVariable Long id) {
        return ResponseEntity.ok(menuItemService.findMenuItemById(id));
    }

    @GetMapping("/restaurant/{restaurantId}")

    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByRestaurantId(@PathVariable Long restaurantId) {
        List<MenuItemResponse> items = menuItemService.findByRestaurantId(restaurantId);
        return ResponseEntity.ok(items);
    }


    @GetMapping("/available")

    public ResponseEntity<List<MenuItemResponse>> getMenuItemsByAvailability(@RequestParam Boolean isAvailable) {
        List<MenuItemResponse> items = menuItemService.findByIsAvailable(isAvailable);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search")

    public ResponseEntity<List<MenuItemResponse>> searchMenuItems(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<MenuItemResponse> items = menuItemService.searchMenuItems(restaurantId, isAvailable, name, minPrice, maxPrice);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<MenuItemResponse> createMenuItem(@RequestBody @Valid MenuItemRequest request) {
        MenuItemResponse created = menuItemService.saveMenuItem(request);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@PathVariable Long id, @RequestBody @Valid MenuItemRequest request) {
        MenuItemResponse updated = menuItemService.updateMenuItem(id, request);
        return ResponseEntity.ok(updated);


    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MenuItemResponse> deleteMenuItem(@PathVariable Long id) {
        MenuItemResponse deleted = menuItemService.deleteMenuItemById(id);
        return ResponseEntity.ok(deleted);


    }
}
