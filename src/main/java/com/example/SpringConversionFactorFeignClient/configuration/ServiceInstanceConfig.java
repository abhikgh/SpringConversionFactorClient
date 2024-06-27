package com.example.SpringConversionFactorFeignClient.configuration;

import com.example.SpringConversionFactorFeignClient.service.ServiceInstanceSupplier;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceInstanceConfig {

    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier(){
        return new ServiceInstanceSupplier("conversion-factor");
    }
}
