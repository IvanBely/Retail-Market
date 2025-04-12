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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PriceServiceImpl(PriceRepository priceRepository, ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<Price> getAllPrices(Pageable pageable) {
        return priceRepository.findAll(pageable);
    }

    @Override
    public Price getPriceById(Long id) {
        return priceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException("Price not found with ID: " + id));
    }

    @Override
    public Price createPrice(PriceRequest priceRequest) {
        Product product = productRepository.findByMaterialNo(priceRequest.getMaterialNo())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with Material No: " + priceRequest.getMaterialNo()));

        Price price = PriceMapper.INSTANCE.toPrice(priceRequest);
        price.setProduct(product);

        return priceRepository.save(price);
    }

    @Override
    public Price updatePriceById(Long id, PriceRequest priceRequest) {

        Price existingPrice = priceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException("Price not found with ID: " + id));

        Price updatedPrice = PriceMapper.INSTANCE.toPrice(priceRequest);

        existingPrice.setChainName(updatedPrice.getChainName());
        existingPrice.setRegularPricePerUnit(updatedPrice.getRegularPricePerUnit());
        existingPrice.setProduct(updatedPrice.getProduct());

        return priceRepository.save(existingPrice);
    }

    @Override
    public void deletePriceById(Long id) {
        Price existingPrice = priceRepository.findById(id)
                .orElseThrow(() -> new PriceNotFoundException("Price not found with ID: " + id));

        priceRepository.delete(existingPrice);
    }
}
