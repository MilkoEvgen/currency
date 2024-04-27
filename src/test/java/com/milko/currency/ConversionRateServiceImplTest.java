package com.milko.currency;

import com.milko.currency.mapper.ConversionRateMapper;
import com.milko.currency.mapper.CurrencyMapper;
import com.milko.currency.model.ConversionRate;
import com.milko.currency.model.Currency;
import com.milko.currency.repository.RateRepository;
import com.milko.currency.service.ConversionRateServiceImpl;
import com.milko.currency.service.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ConversionRateServiceImplTest {
    @Mock
    private RateRepository rateRepository;
    @Mock
    private CurrencyService currencyService;
    @Mock
    private CurrencyMapper currencyMapper;
    @Mock
    private ConversionRateMapper conversionRateMapper;
    @InjectMocks
    private ConversionRateServiceImpl conversionRateService;

    private ConversionRate conversionRate;
    List<ConversionRate> ratesList = new ArrayList<>();

    private Flux<ConversionRate> rates;
    private Flux<ConversionRate> ratesResult;

    private Currency sourceCurrency;

    @BeforeEach
    void init(){
        conversionRate = ConversionRate.builder()
                .sourceCode("USD")
                .destinationCode("PLN")
                .rate(BigDecimal.valueOf(4))
                .build();

        sourceCurrency = Currency.builder()
                .code("USD")
                .description("USD description")
                .active(true)
                .build();

        ratesList.add(conversionRate);

        rates = Flux.fromIterable(ratesList);
        ratesResult = Flux.fromIterable(ratesList);
    }

    @Test
    public void saveAllShouldReturnMonoVoid(){
        Mockito.when(rateRepository.saveAll(any(Iterable.class))).thenReturn(ratesResult);
        Mono<Void> result = conversionRateService.saveAll(rates);

        StepVerifier.create(result)
                .expectSubscription()
                .expectComplete()
                .verify();

        Mockito.verify(rateRepository).saveAll(any(Iterable.class));
    }

    @Test
    public void saveAllShouldThrowException(){
        Mockito.when(rateRepository.saveAll(any(Iterable.class))).thenThrow(new RuntimeException("Mocked exception"));
        Mono<Void> result = conversionRateService.saveAll(rates);

        StepVerifier.create(result)
                .expectSubscription()
                .expectError(RuntimeException.class)
                .verify();

        Mockito.verify(rateRepository).saveAll(any(Iterable.class));
    }

    @Test
    public void getRateByCodesShouldReturnRate(){
        Mockito.when(rateRepository.getRateByCodes(any(String.class), any(String.class))).thenReturn(Mono.just(conversionRate));
        Mockito.when(currencyService.getCurrencyByCode(any(String.class))).thenReturn(Mono.just(sourceCurrency));
    }
}
