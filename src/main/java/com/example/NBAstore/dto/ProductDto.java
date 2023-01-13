package com.example.NBAstore.dto;

import com.example.NBAstore.models.Product;

public class ProductDto {

    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private Integer inventory;
    private Integer categoryId;

    public ProductDto() {}

    public ProductDto(Product product) {
        this.setId(product.getId());
        this.setName(product.getName());
        this.setDescription(product.getDescription());
        this.setImageUrl(product.getImageUrl());
        this.setPrice(product.getPrice());
        this.setInventory(product.getInventory());
        this.setCategoryId(product.getCategory().getId());
    }

    public ProductDto(String name, String description, String imageUrl, double price, Integer inventory, Integer categoryId) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.inventory = inventory;
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
