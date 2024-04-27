package com.milko.currency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConversionRateRequestDto {
    private String sourceCode;
    private String destinationCode;
}
