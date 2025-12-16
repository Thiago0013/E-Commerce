package com.example.commerce.service;

import com.example.commerce.dto.ProductDTO;
import com.example.commerce.model.Product;
import com.example.commerce.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product save(ProductDTO dto){
        Product product = Product.builder()
                .nome(dto.nome())
                .descricao(dto.descricao())
                .preco(dto.preco())
                .quantidadeEstoque(dto.quantidadeEstoque())
                .build();
        return productRepository.save(product);
    }

    public List<Product> list(){
        return productRepository.findAll();
    }
}
