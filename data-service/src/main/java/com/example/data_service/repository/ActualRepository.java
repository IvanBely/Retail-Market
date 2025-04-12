package com.example.data_service.repository;

import com.example.data_service.model.Actual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActualRepository extends JpaRepository<Actual, Long> {
}
