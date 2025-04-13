package com.example.analysis_service.dto;

import com.example.data_service.model.PromoTag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DailyDBDto {
    private LocalDate date;
    private String chainName;
    private String productName;
    private BigDecimal units;
    private BigDecimal price;
    private PromoTag promoTag;
}

