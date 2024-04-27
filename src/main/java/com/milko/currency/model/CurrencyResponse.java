package com.milko.currency.model;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class CurrencyResponse {
    private Boolean success;
    private Map<String, String> symbols;
}
