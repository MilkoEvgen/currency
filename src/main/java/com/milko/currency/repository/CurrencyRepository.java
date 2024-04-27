package com.milko.currency.repository;

import com.milko.currency.model.Currency;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrencyRepository extends R2dbcRepository<Currency, Long> {

    Flux<Currency> findAllByActiveIsTrue();
    @Query("UPDATE currencies SET active = false WHERE code = :code")
    Mono<Void> deactivateCurrency(String code);

    Mono<Currency> findByCode(String code);
}
