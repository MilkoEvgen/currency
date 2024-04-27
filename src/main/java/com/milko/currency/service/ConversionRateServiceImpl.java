package com.milko.currency.service;

import com.milko.currency.dto.ConversionRateDto;
import com.milko.currency.mapper.ConversionRateMapper;
import com.milko.currency.mapper.CurrencyMapper;
import com.milko.currency.model.ConversionRate;
import com.milko.currency.model.Currency;
import com.milko.currency.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConversionRateServiceImpl implements ConversionRateService{
    private final RateRepository rateRepository;
    private final CurrencyService currencyService;
    private final CurrencyMapper currencyMapper;
    private final ConversionRateMapper conversionRateMapper;

    @Override
    public Mono<Void> saveAll(Flux<ConversionRate> rates) {
        log.info("In ConversionRateServiceImpl saveAll()");
        return rates.buffer(1000)
                .flatMap(rateRepository::saveAll)
                .then();
    }

    @Override
    public Mono<ConversionRateDto> getRateByCodes(String sourceCode, String destinationCode) {
        log.info("In ConversionRateServiceImpl getRateByCodes({}/{})", sourceCode, destinationCode);
        return rateRepository.getRateByCodes(sourceCode, destinationCode)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Rate " + sourceCode + "/" + destinationCode + " not found")))
                .flatMap(conversionRate -> {
                    Mono<Currency> sourceCurrency = currencyService.getCurrencyByCode(sourceCode);
                    Mono<Currency> destinationCurrency = currencyService.getCurrencyByCode(destinationCode);

                    return Mono.zip(sourceCurrency, destinationCurrency)
                            .map(tuple -> {
                                return conversionRateMapper.toConversionRateDtoWithCurrencies(conversionRate,
                                        currencyMapper.toCurrencyDto(tuple.getT1()),
                                        currencyMapper.toCurrencyDto(tuple.getT2()));
                            });
                });
    }
}
