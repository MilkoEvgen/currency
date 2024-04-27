package com.milko.currency.service;

import com.milko.currency.model.Currency;
import com.milko.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService{
    private final CurrencyRepository currencyRepository;
    @Override
    public Flux<Currency> getAllCurrencies() {
        return currencyRepository.findAllByActiveIsTrue();
    }

    @Override
    public Mono<Void> saveAllCurrencies(List<Currency> currencies) {
        return currencyRepository.saveAll(currencies).then();
    }

    @Override
    public Mono<Void> deleteAllCurrencies(List<Currency> currencies) {
        return Flux.fromIterable(currencies)
                .flatMap(currency -> currencyRepository.deactivateCurrency(currency.getCode()))
                .then();
    }

    @Override
    public Mono<Currency> getCurrencyByCode(String code) {
        return currencyRepository.findByCode(code);
    }
}
