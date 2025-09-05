package com.app.consumer.RestClient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ProviderRestClient {

    private final RestClient restClient;

    @GetMapping("/instance-info")
    public String getInstanceInfo() {
        return restClient.get()
                .uri("/instance-info")
                .retrieve()
                .body(String.class);
    }
}
