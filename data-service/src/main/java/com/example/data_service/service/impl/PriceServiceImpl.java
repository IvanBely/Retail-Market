package com.example.data_service.service.impl;

import com.example.data_service.dto.PriceRequest;
import com.example.data_service.exception.PriceNotFoundException;
import com.example.data_service.exception.ProductNotFoundException;
import com.example.data_service.mapper.PriceMapper;
import com.example.data_service.model.Price;
import com.example.data_service.model.Product;
import com.example.data_service.repository.PriceRepository;
import com.example.data_service.repository.ProductRepository;
import com.example.data_service.service.PriceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

    private static final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<Price> getAllPrices(Pageable pageable) {
        logger.info("Fetching all prices with pagination: page {}, size {}", pageable.getPageNumber(), pageable.getPageSize());
        return priceRepository.findAll(pageable);
    }

    @Override
    public Price getPriceById(Long id) {
        logger.info("Fetching price with ID: {}", id);
        return priceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Price not found with ID: {}", id);
                    return new PriceNotFoundException("Price not found with ID: " + id);
                });
    }

    @Override
    public Price createPrice(PriceRequest priceRequest) {
        logger.info("Creating new price for product with Material No: {}", priceRequest.getMaterialNo());
        Product product = productRepository.findByMaterialNo(priceRequest.getMaterialNo())
                .orElseThrow(() -> {
                    logger.error("Product not found with Material No: {}", priceRequest.getMaterialNo());
                    return new ProductNotFoundException("Product not found with Material No: " + priceRequest.getMaterialNo());
                });

        Price price = PriceMapper.INSTANCE.toPrice(priceRequest);
        price.setProduct(product);

        Price savedPrice = priceRepository.save(price);
        logger.info("Price created successfully with ID: {}", savedPrice.getId());
        return savedPrice;
    }

    @Override
    public Price updatePriceById(Long id, PriceRequest priceRequest) {
        logger.info("Updating price with ID: {}", id);

        Price existingPrice = priceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Price not found with ID: {}", id);
                    return new PriceNotFoundException("Price not found with ID: " + id);
                });

        Price updatedPrice = PriceMapper.INSTANCE.toPrice(priceRequest);
        existingPrice.setChainName(updatedPrice.getChainName());
        existingPrice.setRegularPricePerUnit(updatedPrice.getRegularPricePerUnit());
        existingPrice.setProduct(updatedPrice.getProduct());

        Price savedPrice = priceRepository.save(existingPrice);
        logger.info("Price updated successfully with ID: {}", savedPrice.getId());
        return savedPrice;
    }

    @Override
    public void deletePriceById(Long id) {
        logger.info("Deleting price with ID: {}", id);

        Price existingPrice = priceRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Price not found with ID: {}", id);
                    return new PriceNotFoundException("Price not found with ID: " + id);
                });

        priceRepository.delete(existingPrice);
        logger.info("Price with ID: {} deleted successfully", id);
    }
}
