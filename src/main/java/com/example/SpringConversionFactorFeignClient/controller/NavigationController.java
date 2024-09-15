package com.example.SpringConversionFactorFeignClient.controller;

import com.example.SpringConversionFactorFeignClient.model.CurrencyConversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class NavigationController {

    @Autowired
    private WebClient.Builder webClientBuilder;

    //http://localhost:8071/getConversionFactorClient/AUD
    @GetMapping(value = "/getConversionFactorClient/{countryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CurrencyConversion> convertCurrency(@PathVariable("countryCode") String countryCode) {

        return webClientBuilder
                .build()
                .get()
                .uri("http://conversion-factor/eureka/v1/getConversionFactor/" + countryCode)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CurrencyConversion.class);

    }


}
