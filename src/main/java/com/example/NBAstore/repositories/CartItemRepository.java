package com.example.NBAstore.repositories;

import com.example.NBAstore.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {}
