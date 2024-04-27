package com.milko.currency.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConversionRateDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private CurrencyDto sourceCurrency;
    private CurrencyDto destinationCurrency;
    private LocalDateTime rateBeginTime;
    private LocalDateTime rateEndTime;
    private BigDecimal rate;
    private String providerCode;
    private BigDecimal multiplier;
    private BigDecimal systemRate;
}