package com.example.analysis_service.repository;

import com.example.analysis_service.dto.MonthlyDBDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyRepository extends JpaRepository<com.example.data_service.entity.MonthlyMaterialized, Long> {

    @Query("""
        SELECT new com.example.analysis_service.dto.MonthlyDBDto(
            m.chainName,
            m.categoryName,
            m.month,
            m.regularUnits,
            m.promoUnits,
            m.promoPercentage
        )
        FROM MonthlyMaterialized m
        """)
    List<MonthlyDBDto> findMonthlyFromMatViewAll();
}
