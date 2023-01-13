package com.example.NBAstore.services;

import com.example.NBAstore.dto.ProductDto;
import com.example.NBAstore.models.Category;
import com.example.NBAstore.models.Product;
import com.example.NBAstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId).get();
    }

    public Product addProduct(ProductDto newProduct) {
        Category category = categoryService.getCategoryById(newProduct.getCategoryId());
        Product product = new Product(newProduct, category);
        return productRepository.save(product);
    }

    public Product updateProduct(ProductDto productDto) {
        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        Product product = productRepository.findById(productDto.getId()).get();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setInventory(productDto.getInventory());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }
}
