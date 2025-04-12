package com.example.data_service.mapper;

import com.example.data_service.dto.PriceRequest;
import com.example.data_service.model.Price;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PriceMapper {

    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    Price toPrice(PriceRequest priceRequest);

}
