package com.example.SpringConversionFactorFeignClient.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public class ServiceInstanceSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays
                .asList(new DefaultServiceInstance(serviceId + "1", serviceId, "conversion-factor", 8061, false),
                      new DefaultServiceInstance(serviceId + "2", serviceId, "conversion-factor", 8062, false)));
    }
}