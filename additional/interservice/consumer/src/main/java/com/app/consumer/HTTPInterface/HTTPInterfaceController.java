package com.app.consumer.HTTPInterface;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/http-interface")
@RequiredArgsConstructor
public class HTTPInterfaceController {

    private final ProviderHTTPInterface providerHTTPInterface;

    @GetMapping("/instance")
    public String getInstanceInfo() {
        return providerHTTPInterface.getInstanceInfo();
    }
}
