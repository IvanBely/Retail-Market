package com.example.analysis_service.controller;

import com.example.analysis_service.dto.DailyDBDto;
import com.example.analysis_service.dto.MonthlyDBDto;
import com.example.analysis_service.dto.request.DailyDtoReq;
import com.example.analysis_service.dto.request.MonthlyDtoReq;
import com.example.analysis_service.service.impl.AnalyticsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsServiceImpl analyticsService;
    @Autowired
    public AnalyticsController(AnalyticsServiceImpl analyticsService) {
        this.analyticsService = analyticsService;
    }

    @PostMapping("/monthly")
    public ResponseEntity<List<MonthlyDBDto>> getMonthlySales(@RequestBody MonthlyDtoReq monthlyDtoReq) {
        List<MonthlyDBDto> sales = analyticsService.getMonthlySales(monthlyDtoReq);
        return ResponseEntity.ok(sales);
    }

    @PostMapping("/monthly/full") // Ручка для полной выгрузки
    public ResponseEntity<List<MonthlyDBDto>> getFullMonthlySales() {
        List<MonthlyDBDto> sales = analyticsService.getFullMonthlySales();
        return ResponseEntity.ok(sales);
    }

    @PostMapping("/daily")
    public ResponseEntity<List<DailyDBDto>> getDailySales(@RequestBody DailyDtoReq dailyDtoReq) {
        List<DailyDBDto> sales = analyticsService.getDailySales(dailyDtoReq);
        return ResponseEntity.ok(sales);
    }

    @PostMapping("/daily/last") // Ручка для выгрузки актуального дня
    public ResponseEntity<List<DailyDBDto>> getDailySalesLastDay() {
        List<DailyDBDto> sales = analyticsService.getDailySalesLastDay();
        return ResponseEntity.ok(sales);
    }

    @PostMapping("/daily/full") // Ручка для выгрузки полной таблички
    public ResponseEntity<List<DailyDBDto>> getFullDailySales() {
        List<DailyDBDto> sales = analyticsService.getFullDailySales();
        return ResponseEntity.ok(sales);
    }
}
