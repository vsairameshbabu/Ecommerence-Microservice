package com.app.consumer.RestTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RestTemplateClient {

    private final RestTemplate restTemplate;

    private static final String PRODUCT_SERVICE_URL = "http://localhost:8081";

    public String getInstanceInfo() {
        return restTemplate.getForObject(PRODUCT_SERVICE_URL + "/instance-info", String.class);

    }
}
