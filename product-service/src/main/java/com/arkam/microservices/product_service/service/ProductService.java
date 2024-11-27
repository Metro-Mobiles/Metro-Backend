package com.arkam.microservices.product_service.service;

import com.arkam.microservices.product_service.controller.ProductResponse;
import com.arkam.microservices.product_service.dto.ProductRequest;
import com.arkam.microservices.product_service.model.Product;
import com.arkam.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully");
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                product.getSkuCode(),
                product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(),
                        product.getSkuCode(),
                        product.getPrice()))
                .toList();
    }

    public Boolean checkId(Long id){
        try {
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            throw new RuntimeException("Product not found");
        }
    }
}
