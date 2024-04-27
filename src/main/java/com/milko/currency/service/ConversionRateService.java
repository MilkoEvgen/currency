package com.milko.currency.service;

import com.milko.currency.dto.ConversionRateDto;
import com.milko.currency.model.ConversionRate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ConversionRateService {
    Mono<Void> saveAll(Flux<ConversionRate> rates);

    Mono<ConversionRateDto> getRateByCodes(String sourceCode, String destinationCode);
}
