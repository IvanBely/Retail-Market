package com.example.analysis_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DailyDtoReq {
    private LocalDate startDate;
    private LocalDate endDate;
//    private Boolean promoOnly; опиционально можно добавить
}
