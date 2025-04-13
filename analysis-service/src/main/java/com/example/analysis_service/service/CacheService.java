package com.example.analysis_service.service;

import java.util.List;

public interface CacheService {
    <T> void cacheData(String key, List<T> data);
}
