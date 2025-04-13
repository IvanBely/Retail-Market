package com.example.analysis_service.service;

import java.time.LocalDate;

public interface MaterializedViewUpdater {
    void refreshAllMaterializedViews();
    void refreshMonthly();
    void refreshDaily(LocalDate localDate);
    void refreshDailyFull();
}
