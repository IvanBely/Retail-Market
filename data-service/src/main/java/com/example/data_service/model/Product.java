package com.example.data_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "material_no")
    private String materialNo;

    @Column(name = "description")
    private String description;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Actual> actualList;
}
