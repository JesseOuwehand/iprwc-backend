package com.example.NBAstore.controllers;

import com.example.NBAstore.dto.ProductDto;
import com.example.NBAstore.models.Product;
import com.example.NBAstore.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
@CrossOrigin(origins = "https://bayoucountry.nl:4200")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable("productId") Integer productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/add")
    public Product addProduct(@RequestBody ProductDto newProduct) {
        return productService.addProduct(newProduct);
    }

    @PostMapping("/update")
    public Product updateProduct(@RequestBody ProductDto productDto) {
        return productService.updateProduct(productDto);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable("productId") Integer productId) { productService.deleteProduct(productId); }
}
