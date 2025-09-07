package com.app.consumer.HTTPInterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class HTTPInterfaceConfig {

    @Bean
    public ProviderHTTPInterface webClientHTTPInterface() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:8081/").build();
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        ProviderHTTPInterface service = factory.createClient(ProviderHTTPInterface.class);
        return service;
    }
}
