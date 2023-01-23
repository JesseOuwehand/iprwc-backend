package com.example.NBAstore.services;

import com.example.NBAstore.dto.CartItemDto;
import com.example.NBAstore.models.CartItem;
import com.example.NBAstore.models.Product;
import com.example.NBAstore.models.ShoppingCart;
import com.example.NBAstore.models.User;
import com.example.NBAstore.repositories.CartItemRepository;
import com.example.NBAstore.repositories.ProductRepository;
import com.example.NBAstore.repositories.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ShoppingCartService(
            ShoppingCartRepository shoppingCartRepository,
            CartItemRepository cartItemRepository,
            ProductRepository productRepository
            ) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public ShoppingCart getShoppingCart(User user) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findShoppingCartByUser(user);

        if (shoppingCart.isPresent()) {
            return shoppingCart.get();
        } else {
            ShoppingCart newShoppingCart = new ShoppingCart(user);
            return shoppingCartRepository.save(newShoppingCart);
        }
    }

    public CartItem addItemToCart(CartItemDto cartItemDto, User user) {
        ShoppingCart shoppingCart = getShoppingCart(user);
        Product product = productRepository.findById(cartItemDto.getProductId()).get();
        CartItem newCartItem = new CartItem(
                shoppingCart,
                product,
                cartItemDto.getQuantity()
        );
        return cartItemRepository.save(newCartItem);
    }

    public void deleteFromCart(Integer cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    
    public void clearShoppingCart(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser(user).get();
        List<CartItem> cartItems = shoppingCart.getCartItems();
        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }
    }
}
