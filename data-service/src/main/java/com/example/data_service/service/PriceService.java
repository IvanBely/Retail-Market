package com.example.data_service.service;

import com.example.data_service.dto.PriceRequest;
import com.example.data_service.model.Price;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PriceService {
    Page<Price> getAllPrices(Pageable pageable);

    Price getPriceById(Long id);

    Price createPrice(PriceRequest priceRequest);

    Price updatePriceById(Long id, PriceRequest priceRequest);

    void deletePriceById(Long id);

}
