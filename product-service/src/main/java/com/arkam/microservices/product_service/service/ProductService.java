package com.arkam.microservices.product_service.service;

import com.arkam.microservices.product_service.model.Product;
import com.arkam.microservices.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public Product createProduct(Product product) {
        product.setTotalAmount(product.getUnitPrice() * product.getQuantity() - product.getDiscount());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id);
        existingProduct.setImage(updatedProduct.getImage());
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
        existingProduct.setUnitCost(updatedProduct.getUnitCost());
        existingProduct.setDiscount(updatedProduct.getDiscount());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setSupplierName(updatedProduct.getSupplierName());
        existingProduct.setQuantity(updatedProduct.getQuantity());
        existingProduct.setTotalAmount(updatedProduct.getUnitPrice() * updatedProduct.getQuantity() - updatedProduct.getDiscount());
        existingProduct.setSkuCode(updatedProduct.getSkuCode());
        return productRepository.save(existingProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
