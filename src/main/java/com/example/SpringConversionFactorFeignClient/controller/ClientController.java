package com.example.SpringConversionFactorFeignClient.controller;

import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.SpringConversionFactorFeignClient.model.Currency;
import com.example.SpringConversionFactorFeignClient.service.FeignClientService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ClientController {

	@Autowired
	private LoadBalancerClient loadBalancerClient;
	
	@Autowired
	private FeignClientService feignClientService;
	
	// http://localhost:8071/convertCurrency
	/*{
		  "countryCode" : "IND",
		  "amount" : 10000
	}*/
	@PostMapping(value="/convertCurrency",produces="application/json")
	public ResponseEntity<Currency> convertCurrency(@RequestBody Currency currency){
		log.info("in feign call..." + currency.getCountryCode());
		ResponseEntity<Double> conversionFactorRE = feignClientService.getConversionFactor(currency.getCountryCode());
		Double conversionFactor = conversionFactorRE.getBody().doubleValue();
		log.info("conversionFactor : " + conversionFactor);
		
		double amount = currency.getAmount() / conversionFactor;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		amount = Double.parseDouble(df.format(amount));
		Currency currency2 = new Currency("USD", amount);
		return new ResponseEntity<>(currency2, HttpStatus.OK);
	}
	
	
	// http://localhost:8071/convertCurrencyRibbon
	@PostMapping(value="/convertCurrencyRibbon",produces="application/json")
	public ResponseEntity<Currency> convertCurrencyRibbon(@RequestBody Currency currency) throws URISyntaxException{
		
		Currency currency2 = null;
		
		String uriServiceHead = loadBalancerClient.choose("conversion-factor").getUri().toString();
				
		URI uri = new URI(uriServiceHead+"/eureka/v1/getConversionFactor/"+currency.getCountryCode());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);
		
		RestTemplate restTemplate = new RestTemplate();
		Double conversionFactor = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, Double.class).getBody();
		
		log.info("conversionFactor " + conversionFactor);
		
		double amount = currency.getAmount() / conversionFactor;
		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);
		amount = Double.parseDouble(df.format(amount));
		currency2 = new Currency("USD", amount);
		return new ResponseEntity<>(currency2, HttpStatus.OK);
	
	}
	
}
