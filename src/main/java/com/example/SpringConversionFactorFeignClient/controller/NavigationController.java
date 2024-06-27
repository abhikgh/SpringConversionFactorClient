package com.example.SpringConversionFactorFeignClient.controller;

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
    public Mono<String> convertCurrency(@PathVariable("countryCode") String countryCode) {

        return webClientBuilder.build()
                .get()
                .uri("http://conversion-factor/eureka/v1/getConversionFactor/" + countryCode)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class);

    }

    // http://localhost:8071/convertCurrency/AUD
	/*{
		  "countryCode" : "IND",
		  "amount" : 10000
	}*/
   /* @PostMapping(value="/convertCurrency/{countryCodeTo}",produces="application/json")
    public ResponseEntity<Currency> convertCurrency(@PathVariable("countryCodeTo") String countryCodeTo,@RequestBody Currency currencyFrom){
        ResponseEntity<Double> conversionFactorREFrom = feignClientService.getConversionFactor(currencyFrom.getCountryCode());
        Double conversionFactorFrom = conversionFactorREFrom.getBody().doubleValue();
        double amountFrom = currencyFrom.getAmount() / conversionFactorFrom;
        ResponseEntity<Double> conversionFactorRETo = feignClientService.getConversionFactor(countryCodeTo);
        Double conversionFactorTo = conversionFactorRETo.getBody().doubleValue();
        double amountTo = amountFrom * conversionFactorTo;

        System.out.println("amountTo = " + amountTo);
        log.debug("amountTo::" + amountTo);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        amountTo = Double.parseDouble(df.format(amountTo));
        Currency currency2 = new Currency(countryCodeTo, amountTo);
        return new ResponseEntity<>(currency2, HttpStatus.OK);
    }*/


}
