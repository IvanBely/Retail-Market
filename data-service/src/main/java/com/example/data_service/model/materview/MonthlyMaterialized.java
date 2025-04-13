package com.example.data_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@Entity
@Table(name = "monthly_materialized")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyMaterialized {

    @Id
    private Long id;
    private String chainName;
    private String categoryName;
    private YearMonth month;
    private Double regularUnits;
    private Double promoUnits;
    private Double promoPercentage;
}
