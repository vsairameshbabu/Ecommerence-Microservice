package com.app.consumer.OpenFiegn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "product-service", url = "http://localhost:8081")
public interface ProviderFeignClient {

    @GetMapping("/instance-info")
    public String getInstanceInfo();

}
