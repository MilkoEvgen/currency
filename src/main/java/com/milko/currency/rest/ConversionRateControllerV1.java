package com.milko.currency.rest;

import com.milko.currency.dto.ConversionRateDto;
import com.milko.currency.dto.ConversionRateRequestDto;
import com.milko.currency.service.ConversionRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/rates")
public class ConversionRateControllerV1 {
    private final ConversionRateService conversionRateService;

    @GetMapping
    public Mono<ConversionRateDto> getRateByCodes(@RequestBody ConversionRateRequestDto requestDto){
        return conversionRateService.getRateByCodes(requestDto.getSourceCode(), requestDto.getDestinationCode());
    }
}
