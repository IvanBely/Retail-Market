package com.example.analysis_service.repository;

import com.example.analysis_service.dto.DailyDBDto;
import com.example.data_service.model.materview.DailyMaterialized;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailyRepository extends JpaRepository<DailyMaterialized, Long> {

    @Query("""
        SELECT new com.example.analysis_service.dto.DailyDBDto(
            d.chainName,
            d.categoryName,
            d.date,
            d.regularUnits,
            d.promoUnits,
            d.promoPercentage
        )
        FROM DailyMaterialized d
        """)
    List<DailyDBDto> findDailyFromMatViewAll();

    @Query("""
        SELECT new com.example.analysis_service.dto.DailyDBDto(
            d.chainName,
            d.categoryName,
            d.date,
            d.regularUnits,
            d.promoUnits,
            d.promoPercentage
        )
        FROM DailyMaterialized d
        WHERE d.date = :date
        """)
    List<DailyDBDto> findDailyFromMatViewByDate(@Param("date") LocalDate date);
}
