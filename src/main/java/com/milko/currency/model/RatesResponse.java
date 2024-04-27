package com.milko.currency.model;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RatesResponse {
    private Boolean success;
    private String base;
    private LocalDate date;
    private Map<String, Double> rates;
}
