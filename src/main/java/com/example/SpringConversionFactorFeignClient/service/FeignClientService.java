package com.example.SpringConversionFactorFeignClient.service;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="conversion-factor")
@RibbonClient(name="conversion-factor")
public interface FeignClientService {

	@GetMapping(value="/eureka/v1/getConversionFactor/{countryCode}",produces="application/json") 
	public ResponseEntity<Double> getConversionFactor(@PathVariable("countryCode") String countryCode);

}

