package com.milko.currency.service;

import com.milko.currency.model.ConversionRate;
import com.milko.currency.model.Currency;
import com.milko.currency.model.CurrencyResponse;
import com.milko.currency.model.RatesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class ScheduleService {
    private final CurrencyService currencyService;
    private final ConversionRateService conversionRateService;
    private final WebClient webClient;
    @Value("${currency.api.key}")
    private String currencyApiKey;


    @Scheduled(initialDelay = 1000, fixedDelay = 86400000)
    public void updateCurrencyData() {
        saveCurrencies()
                .then(saveRates())
                .subscribe();
    }

    public Mono<Void> saveCurrencies() {
        log.info("In saveCurrencies()");
        return webClient.get()
                .uri("/symbols?access_key=" + currencyApiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class).flatMap(errorBody ->
                                Mono.error(new ResponseStatusException(response.statusCode(), "Error: " + errorBody))))
                .bodyToMono(CurrencyResponse.class)
                .flatMap(currencyResponse -> {

                    List<Currency> apiCurrencies = currencyResponse.getSymbols().entrySet()
                            .stream().map(this::createCurrency)
                            .toList();

                    return currencyService.getAllCurrencies().collectList()
                            .flatMap(dbCurrencies -> {
                                List<Currency> newCurrencies = apiCurrencies.stream()
                                        .filter(apiCurrency -> dbCurrencies.stream()
                                                .noneMatch(dbCurrency -> dbCurrency.getCode().equals(apiCurrency.getCode())))
                                        .collect(Collectors.toList());
                                List<Currency> currenciesToDelete = dbCurrencies.stream()
                                        .filter(dbCurrency -> apiCurrencies.stream()
                                                .noneMatch(apiCurrency -> apiCurrency.getCode().equals(dbCurrency.getCode())))
                                        .collect(Collectors.toList());
                                Mono<Void> saveMono = currencyService.saveAllCurrencies(newCurrencies);
                                Mono<Void> deleteMono = currencyService.deleteAllCurrencies(currenciesToDelete);
                                log.info("In saveCurrencies() currencies saved");
                                return saveMono.then(deleteMono);
                            });

                }).then();
    }

    public Mono<Void> saveRates() {
        log.info("In saveRates()");
        return webClient.get()
                .uri("/latest?access_key=" + currencyApiKey)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class).flatMap(errorBody ->
                                Mono.error(new ResponseStatusException(response.statusCode(), "Error: " + errorBody))))
                .bodyToMono(RatesResponse.class)
                .flatMapMany(ratesResponse -> {
                    return Flux.fromIterable(ratesResponse.getRates().entrySet())
                            .flatMap(entry1 -> {
                                return Flux.fromIterable(ratesResponse.getRates().entrySet())
                                        .filter(entry2 -> !entry2.getKey().equals(entry1.getKey()))
                                        .map(entry2 -> createConversionRate(entry1, entry2));
                            });
                }).transform(conversionRateService::saveAll)
                .then();
    }

    private Currency createCurrency(Map.Entry<String, String> entry) {
        return Currency.builder()
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .code(entry.getKey())
                .description(entry.getValue())
                .active(true)
                .build();
    }

    private ConversionRate createConversionRate(Map.Entry<String, Double> entry1, Map.Entry<String, Double> entry2) {
        BigDecimal rate1 = BigDecimal.valueOf(entry1.getValue());
        BigDecimal rate2 = BigDecimal.valueOf(entry2.getValue());
        BigDecimal resultRate = rate2.divide(rate1, 6, RoundingMode.HALF_UP);

        LocalDateTime localDateTime = LocalDateTime.now();

        return ConversionRate.builder()
                .createdAt(localDateTime)
                .modifiedAt(localDateTime)
                .sourceCode(entry1.getKey())
                .destinationCode(entry2.getKey())
                .rateBeginTime(localDateTime)
                .rateEndTime(localDateTime.plusDays(1))
                .rate(resultRate)
                .providerCode("fix")
                .multiplier(BigDecimal.ONE)
                .systemRate(BigDecimal.ONE)
                .build();
    }
}
