package com.example.E_Commerce_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product_category")
public class ProductsCategory {

    @Id
    private Short categoryId;

    @Column(nullable = false,unique = true)
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Products> products;

}
