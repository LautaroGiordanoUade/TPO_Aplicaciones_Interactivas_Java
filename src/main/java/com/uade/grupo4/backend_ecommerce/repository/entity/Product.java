package com.uade.grupo4.backend_ecommerce.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean featured;

    @OneToMany(mappedBy = "product")
    private Set<FavoriteProduct> favorites = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private Set<ViewedProduct> viewed = new HashSet<>();
}
