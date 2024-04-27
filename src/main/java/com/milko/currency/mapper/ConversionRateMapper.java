package com.milko.currency.mapper;

import com.milko.currency.dto.ConversionRateDto;
import com.milko.currency.dto.CurrencyDto;
import com.milko.currency.model.ConversionRate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversionRateMapper {

    @Mapping(target = "sourceCurrency", ignore = true)
    @Mapping(target = "destinationCurrency", ignore = true)
    ConversionRateDto toConversionRateDto(ConversionRate conversionRate);

    default ConversionRateDto toConversionRateDtoWithCurrencies(ConversionRate conversionRate,
                                                                CurrencyDto sourceCurrency,
                                                                CurrencyDto destinationCurrency){
        ConversionRateDto conversionRateDto = toConversionRateDto(conversionRate);
        conversionRateDto.setSourceCurrency(sourceCurrency);
        conversionRateDto.setDestinationCurrency(destinationCurrency);
        return conversionRateDto;
    }
}
