package com.emirkoral.deliveryapp.menuitem.mapper;


import com.emirkoral.deliveryapp.menuitem.MenuItem;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemRequest;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant.id", source = "restaurantId")
    MenuItem toEntity(MenuItemRequest request);

    @Mapping(target = "restaurantId", source = "restaurant.id")
    MenuItemResponse toResponse(MenuItem menuItem);
}
