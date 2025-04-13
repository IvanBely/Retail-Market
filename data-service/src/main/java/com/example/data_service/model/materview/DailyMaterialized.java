package com.example.data_service.model.materview;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "daily_materialized")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyMaterialized {

    @Id
    private Long id;
    private String chainName;
    private String categoryName;
    private LocalDate date;
    private Double regularUnits;
    private Double promoUnits;
    private Double promoPercentage;
}
