package com.emirkoral.deliveryapp.menuitem.mapper;


import com.emirkoral.deliveryapp.menuitem.MenuItem;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemRequest;
import com.emirkoral.deliveryapp.menuitem.dto.MenuItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    MenuItem toEntity(MenuItemRequest request);

    MenuItemResponse toResponse(MenuItem menuItem);

    void updateEntityFromRequest(MenuItemRequest request, @MappingTarget MenuItem menuItem);
}
