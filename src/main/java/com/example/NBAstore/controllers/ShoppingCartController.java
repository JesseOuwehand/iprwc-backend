package com.example.NBAstore.controllers;

import com.example.NBAstore.dto.CartItemDto;
import com.example.NBAstore.models.CartItem;
import com.example.NBAstore.models.ShoppingCart;
import com.example.NBAstore.models.User;
import com.example.NBAstore.services.AuthenticationService;
import com.example.NBAstore.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@CrossOrigin(origins = "https://bayoucountry.nl:4200")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final AuthenticationService authenticationService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, AuthenticationService authenticationService) {
        this.shoppingCartService = shoppingCartService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ShoppingCart getShoppingCart() {
        User user = authenticationService.getAuthenticatedUser();
        return shoppingCartService.getShoppingCart(user);
    }

    @PostMapping("/add")
    public CartItem addItemToCart(@RequestBody CartItemDto cartItemDto) {
        User user = authenticationService.getAuthenticatedUser();
        return shoppingCartService.addItemToCart(cartItemDto, user);
    }

    @DeleteMapping("/{cartItemId}")
    public void deleteFromCart(@PathVariable("cartItemId") Integer cartItemId) {
        shoppingCartService.deleteFromCart(cartItemId);
    }
}
