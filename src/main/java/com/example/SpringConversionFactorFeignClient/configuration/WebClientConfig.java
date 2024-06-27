package com.example.SpringConversionFactorFeignClient.configuration;

import com.example.SpringConversionFactorFeignClient.service.DemoServerInstanceConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@LoadBalancerClient(name = "conversion-factor", configuration = DemoServerInstanceConfiguration.class)
class WebClientConfig {

    @LoadBalanced
    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

}