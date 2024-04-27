package com.milko.currency.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("conversion_rates")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConversionRate {
    @Id
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String sourceCode;
    private String destinationCode;
    private LocalDateTime rateBeginTime;
    private LocalDateTime rateEndTime;
    private BigDecimal rate;
    private String providerCode;
    private BigDecimal multiplier;
    private BigDecimal systemRate;
}
