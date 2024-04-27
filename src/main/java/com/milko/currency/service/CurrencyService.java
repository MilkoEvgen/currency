package com.milko.currency.service;

import com.milko.currency.model.Currency;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface CurrencyService {
    Flux<Currency> getAllCurrencies();
    Mono<Void> saveAllCurrencies(List<Currency> currencies);
    Mono<Void> deleteAllCurrencies(List<Currency> currencies);
    Mono<Currency> getCurrencyByCode(String code);
}
