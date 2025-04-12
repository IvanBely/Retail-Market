package com.example.data_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "actuals")
public class Actual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_code", referencedColumnName = "address_code")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_no", referencedColumnName = "material_no")
    private Product product;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "units")
    private BigDecimal units;

    @Column(name = "actual_price")
    private BigDecimal actualPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "promo_tag")
    private PromoTag promoTag;
}
