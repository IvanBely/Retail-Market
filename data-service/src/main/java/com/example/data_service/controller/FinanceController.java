package com.example.data_service.controller;

import com.example.data_service.dto.PriceRequest;
import com.example.data_service.model.Price;
import com.example.data_service.service.PriceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/finance")
public class FinanceController {

    private final PriceService priceService;

    @Autowired
    public FinanceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/prices")
    public ResponseEntity<Page<Price>> getAllPrices(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Price> prices = priceService.getAllPrices(pageable);

        return ResponseEntity.ok(prices);
    }

    @GetMapping("/prices/{id}")
    public ResponseEntity<Price> getPriceById(@PathVariable Long id) {
        Price price = priceService.getPriceById(id);
        return ResponseEntity.ok(price);
    }

    @PostMapping("/prices")
    public ResponseEntity<Price> createPrice(@RequestBody @Valid PriceRequest priceRequest) {
        Price savedPrice = priceService.createPrice(priceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPrice);
    }

    @PutMapping("/prices/{id}")
    public ResponseEntity<Price> updatePrice(@PathVariable Long id, @RequestBody @Valid PriceRequest priceRequest) {
        Price updatedPrice = priceService.updatePriceById(id, priceRequest);
        return ResponseEntity.ok(updatedPrice);

    }

    @DeleteMapping("/prices/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable Long id) {

        priceService.deletePriceById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
