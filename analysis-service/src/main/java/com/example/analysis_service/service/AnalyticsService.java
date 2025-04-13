package com.example.analysis_service.service;

import com.example.analysis_service.dto.DailyDBDto;
import com.example.analysis_service.dto.MonthlyDBDto;
import com.example.analysis_service.dto.request.DailyDtoReq;
import com.example.analysis_service.dto.request.MonthlyDtoReq;

import java.util.List;

public interface AnalyticsService {
    List<MonthlyDBDto> getMonthlySales(MonthlyDtoReq monthlyDtoReq);

    List<DailyDBDto> getDailySales(DailyDtoReq dailyDtoReq);

    List<DailyDBDto> getDailySalesLastDay();

    List<MonthlyDBDto> getFullMonthlySales();

    List<DailyDBDto> getFullDailySales();
}
