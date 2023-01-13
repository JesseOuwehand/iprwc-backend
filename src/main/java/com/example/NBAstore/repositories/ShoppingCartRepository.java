package com.example.NBAstore.repositories;

import com.example.NBAstore.models.ShoppingCart;
import com.example.NBAstore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    Optional<ShoppingCart> findShoppingCartByUser(User user);
}
