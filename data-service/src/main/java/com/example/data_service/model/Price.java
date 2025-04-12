package com.example.data_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "price")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_no", referencedColumnName = "material_no")
    private Product product;

    @Column(name = "chain_name")
    private String chainName;

    @Column(name = "regular_price_per_unit")
    private BigDecimal regularPricePerUnit;

}
