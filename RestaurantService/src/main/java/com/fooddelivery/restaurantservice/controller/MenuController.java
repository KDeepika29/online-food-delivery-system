package com.fooddelivery.restaurantservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.restaurantservice.service.MenuService;
import com.fooddelivery.restaurantservice.entity.MenuItemEntity;
import com.fooddelivery.restaurantservice.to.MenuItemTO;
import com.fooddelivery.restaurantservice.to.SearchResultTO;

@RestController
@ResponseBody
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	MenuService menuService;
	
    @PostMapping
    public ResponseEntity<MenuItemEntity> createMenu(@RequestBody MenuItemTO menuTO) {
		menuService.validateMenuTO(menuTO);
		menuTO.setId(null);
		MenuItemEntity createdMenuItem = menuService.createMenu(menuTO);
		return ResponseEntity.ok(createdMenuItem);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemEntity> updateMenu(@RequestBody MenuItemTO menuTO, @PathVariable int menuItemId) {
		menuService.checkIfMenuItemIdExist(menuItemId);
		menuService.validateMenuTO(menuTO);
		menuTO.setId(menuItemId);
		MenuItemEntity updatedMenuItem = menuService.updateMenu(menuTO);
		return ResponseEntity.ok(updatedMenuItem);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<String> deleteMenu(@PathVariable int menuItemId) {	
		menuService.checkIfMenuItemIdExist(menuItemId);
		menuService.deleteMenu(menuItemId);
		return ResponseEntity.ok("Menu item deleted successfully.");
    }
	
}
