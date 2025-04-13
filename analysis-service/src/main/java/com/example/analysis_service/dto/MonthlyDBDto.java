package com.example.analysis_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
@AllArgsConstructor
public class MonthlyDBDto {
    private String chainName;
    private String categoryName;
    private YearMonth month;
    private BigDecimal regularUnits;
    private BigDecimal promoUnits;
    private BigDecimal promoPercentage;
}

