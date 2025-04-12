package com.example.data_service.repository;

import com.example.data_service.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findById(Long id);

}
