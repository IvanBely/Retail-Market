package com.example.data_service.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {
    @JsonAlias({"material_no", "Material No"})
    @NotBlank(message = "Material number must not be blank")
    private String materialNo;

    @JsonAlias({"chain_name", "Chain_name"})
    @NotBlank(message = "Chain name must not be blank")
    private String chainName;

    @JsonAlias({"regular_price_per_unit", "Regular price per unit"})
    @NotNull(message = "Regular price must not be null")
    @Positive(message = "Regular price must be greater than 0")
    private BigDecimal regularPricePerUnit;
}
