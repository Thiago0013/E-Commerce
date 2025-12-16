package com.example.commerce.controller;

import com.example.commerce.dto.ProductDTO;
import com.example.commerce.model.Product;
import com.example.commerce.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductDTO dto){
        return productService.save(dto);
    }

    @GetMapping
    public List<Product> list(){
        return productService.list();
    }
}
