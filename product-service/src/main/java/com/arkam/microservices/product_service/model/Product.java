package com.arkam.microservices.product_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "t_products")
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedV
    private String id;
    private String name;
    private String description;
    private String skuCode;
    private BigDecimal price;
}
