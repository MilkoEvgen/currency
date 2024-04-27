package com.milko.currency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CurrencyDto {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String code;
    private String description;
    private Boolean active;
}
