package com.example.analysis_service.service.impl;

import com.example.analysis_service.util.RedisCacheKeys;
import com.example.analysis_service.dto.DailyDBDto;
import com.example.analysis_service.dto.MonthlyDBDto;
import com.example.analysis_service.dto.request.DailyDtoReq;
import com.example.analysis_service.dto.request.MonthlyDtoReq;
import com.example.analysis_service.repository.DailyRepository;
import com.example.analysis_service.repository.MonthlyRepository;
import com.example.analysis_service.service.AnalyticsService;
import com.example.analysis_service.service.CacheService;
import com.example.data_service.annotation.UseReplica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@UseReplica
public class AnalyticsServiceImpl implements AnalyticsService {

    private static final Logger logger = LoggerFactory.getLogger(AnalyticsServiceImpl.class);

    private final MonthlyRepository monthlyRepository;
    private final DailyRepository dailyRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final CacheService cacheService;
    private final MaterializedViewUpdaterImpl materializedViewUpdater;

    @Autowired
    public AnalyticsServiceImpl(MonthlyRepository monthlyRepository,
                                DailyRepository dailyRepository,
                                RedisTemplate<String, Object> redisTemplate,
                                CacheService cacheService,
                                MaterializedViewUpdaterImpl materializedViewUpdater) {
        this.monthlyRepository = monthlyRepository;
        this.dailyRepository = dailyRepository;
        this.redisTemplate = redisTemplate;
        this.cacheService = cacheService;
        this.materializedViewUpdater = materializedViewUpdater;
    }

    @Override
    public List<MonthlyDBDto> getMonthlySales(MonthlyDtoReq request) {
        logger.info("Fetching monthly sales data with filter: {}", request);

        List<MonthlyDBDto> allData = getOrFetchMonthlySalesFromCache();
        LocalDate startDate = Optional.ofNullable(request.getStartDate()).orElse(LocalDate.MIN);
        LocalDate endDate = Optional.ofNullable(request.getEndDate()).orElse(LocalDate.now());

        validateDateRange(startDate, endDate);

        return allData.stream()
                .filter(dto -> {
                    LocalDate dtoDate = dto.getMonth().atDay(1);
                    return !dtoDate.isBefore(startDate) && !dtoDate.isAfter(endDate);
                })
                .toList();
    }

    @Override
    public List<MonthlyDBDto> getFullMonthlySales() {
        logger.info("Fetching full monthly sales data.");
        materializedViewUpdater.refreshMonthly();
        return fetchAndCacheMonthlySales();
    }

    @Override
    public List<DailyDBDto> getDailySales(DailyDtoReq request) {
        logger.info("Fetching daily sales data with filter: {}", request);

        List<DailyDBDto> allData = getOrFetchDailySalesFromCache();
        LocalDate startDate = Optional.ofNullable(request.getStartDate()).orElse(LocalDate.MIN);
        LocalDate endDate = Optional.ofNullable(request.getEndDate()).orElse(LocalDate.now());

        validateDateRange(startDate, endDate);

        return allData.stream()
                .filter(dto -> {
                    LocalDate dtoDate = dto.getDate();
                    return !dtoDate.isBefore(startDate) && !dtoDate.isAfter(endDate);
                })
                .toList();
    }

    @Override
    public List<DailyDBDto> getDailySalesLastDay() {
        logger.info("Fetching daily sales for the last day.");

        LocalDate today = LocalDate.now();
        final String cacheKey = RedisCacheKeys.dailySalesKey(today);

        materializedViewUpdater.refreshDaily(today);

        try {
            List<DailyDBDto> data = dailyRepository.findDailyFromMatViewByDate(today);
            cacheService.cacheData(cacheKey, data);
            return data;
        } catch (Exception e) {
            logger.error("Error fetching daily sales for the last day", e);
            throw new RuntimeException("Failed to fetch daily sales for today", e);
        }
    }

    @Override
    public List<DailyDBDto> getFullDailySales() {
        logger.info("Fetching full daily sales data.");
        materializedViewUpdater.refreshDailyFull();
        return fetchAndCacheDailySales();
    }


    private List<MonthlyDBDto> getOrFetchMonthlySalesFromCache() {
        List<MonthlyDBDto> data = (List<MonthlyDBDto>) redisTemplate.opsForValue().get(RedisCacheKeys.MONTHLY_SALES_FULL);
        if (data == null) {
            logger.info("Cache miss for monthly sales.");
            data = fetchAndCacheMonthlySales();
        }
        return data;
    }

    private List<DailyDBDto> getOrFetchDailySalesFromCache() {
        List<DailyDBDto> data = (List<DailyDBDto>) redisTemplate.opsForValue().get(RedisCacheKeys.DAILY_SALES_FULL);
        if (data == null) {
            logger.info("Cache miss for daily sales.");
            materializedViewUpdater.refreshDailyFull();
            data = fetchAndCacheDailySales();
        }
        return data;
    }

    private List<MonthlyDBDto> fetchAndCacheMonthlySales() {
        try {
            List<MonthlyDBDto> data = monthlyRepository.findMonthlyFromMatViewAll();
            cacheService.cacheData(RedisCacheKeys.MONTHLY_SALES_FULL, data);
            return data;
        } catch (Exception e) {
            logger.error("Error fetching monthly sales", e);
            throw new RuntimeException("Failed to fetch monthly sales from DB", e);
        }
    }

    private List<DailyDBDto> fetchAndCacheDailySales() {
        try {
            List<DailyDBDto> data = dailyRepository.findDailyFromMatViewAll();
            cacheService.cacheData(RedisCacheKeys.DAILY_SALES_FULL, data);
            return data;
        } catch (Exception e) {
            logger.error("Error fetching daily sales", e);
            throw new RuntimeException("Failed to fetch daily sales from DB", e);
        }
    }

    private void validateDateRange(LocalDate start, LocalDate end) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }
}
