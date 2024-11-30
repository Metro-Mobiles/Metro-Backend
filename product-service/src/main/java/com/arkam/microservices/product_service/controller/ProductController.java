package com.arkam.microservices.product_service.controller;

import com.arkam.microservices.product_service.model.Product;
import com.arkam.microservices.product_service.service.ImageUploadService;
import com.arkam.microservices.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ImageUploadService imageUploadService;


    @Autowired
    public ProductController(ProductService productService, ImageUploadService imageUploadService) {
        this.productService = productService;
        this.imageUploadService = imageUploadService;
    }

    @GetMapping("/retrieveAll")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Product> createProduct(
            @RequestParam("image") MultipartFile image,
            @RequestParam("name") String name,
            @RequestParam("unitPrice") Double unitPrice,
            @RequestParam("unitCost") Double unitCost,
            @RequestParam("discount") Double discount,
            @RequestParam("category") String category,
            @RequestParam("supplierName") String supplierName,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("skuCode") String skuCode) {

        // Upload the image to S3 and get the URL
        String imageUrl = imageUploadService.uploadFile(image);

        // Create the product with the image URL and other data
        Product product = new Product(imageUrl, name, unitPrice, unitCost, discount, category, supplierName, quantity, unitPrice * quantity - discount, skuCode);

        // Save the product and return it
        Product savedProduct = productService.createProduct(product, image);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "unitPrice", required = false) Double unitPrice,
            @RequestParam(value = "unitCost", required = false) Double unitCost,
            @RequestParam(value = "discount", required = false) Double discount,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "supplierName", required = false) String supplierName,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "skuCode", required = false) String skuCode) {

        // Find the existing product
        Product existingProduct = productService.getProductById(id);

        // If a new image is provided, upload it and update the image URL
        if (image != null && !image.isEmpty()) {
            String imageUrl = imageUploadService.uploadFile(image);
            existingProduct.setImage(imageUrl);
        }

        // Update other product details if they were provided
        if (name != null) existingProduct.setName(name);
        if (unitPrice != null) existingProduct.setUnitPrice(unitPrice);
        if (unitCost != null) existingProduct.setUnitCost(unitCost);
        if (discount != null) existingProduct.setDiscount(discount);
        if (category != null) existingProduct.setCategory(category);
        if (supplierName != null) existingProduct.setSupplierName(supplierName);
        if (quantity != null) existingProduct.setQuantity(quantity);
        if (skuCode != null) existingProduct.setSkuCode(skuCode);

        // Recalculate total amount
        existingProduct.setTotalAmount(existingProduct.getUnitPrice() * existingProduct.getQuantity() - existingProduct.getDiscount());

        // Save and return the updated product
        Product updatedProduct = productService.updateProduct(id, existingProduct, image);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
