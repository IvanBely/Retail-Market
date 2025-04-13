package com.example.analysis_service.util;

import java.time.LocalDate;

/**
 * Утилитный класс для хранения ключей кэша Redis,
 * используемых в AnalyticsService.
 */
public final class RedisCacheKeys {

    // Ключи для полного кэша
    public static final String MONTHLY_SALES_FULL = "monthly_sales:full";
    public static final String DAILY_SALES_FULL = "daily_sales:full";

    private RedisCacheKeys() {
        // Предотвращаем создание экземпляра
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    /**
     * Возвращает кэш-ключ для дневных продаж на конкретную дату.
     *
     * @param date Дата (обычно — сегодня)
     * @return Строка ключа для Redis
     */
    public static String dailySalesKey(LocalDate date) {
        return "daily_sales:" + date;
    }
}
