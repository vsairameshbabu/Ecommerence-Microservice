package com.app.consumer.HTTPInterface;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProviderHTTPInterface {

    @GetExchange("/instance-info")
    String getInstanceInfo();
}
