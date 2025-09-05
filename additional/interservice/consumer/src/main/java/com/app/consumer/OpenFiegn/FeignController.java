package com.app.consumer.OpenFiegn;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/feign")
@RequiredArgsConstructor
public class FeignController {

    private final ProviderFeignClient providerFeignClient;

    @GetMapping("/instance-info")
    public String getInstanceInfo() {
        return providerFeignClient.getInstanceInfo();
    }
}
