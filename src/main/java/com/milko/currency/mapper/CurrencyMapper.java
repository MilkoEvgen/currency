package com.milko.currency.mapper;

import com.milko.currency.dto.CurrencyDto;
import com.milko.currency.model.Currency;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {
    CurrencyDto toCurrencyDto(Currency currency);
}
