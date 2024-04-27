package com.milko.currency.repository;

import com.milko.currency.model.ConversionRate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface RateRepository extends R2dbcRepository <ConversionRate, Long> {

    @Query("SELECT * FROM conversion_rates WHERE source_code = :sourceCode AND destination_code = :destinationCode " +
            "ORDER BY created_at DESC LIMIT 1")
    Mono<ConversionRate> getRateByCodes(String sourceCode, String destinationCode);
}
