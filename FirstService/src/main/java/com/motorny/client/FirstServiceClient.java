package com.motorny.client;

import com.motorny.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "second-service",
        url = "${second.service.config.url}",
        configuration = FeignConfig.class
)
public interface FirstServiceClient {

    @GetMapping("/api/hello-world")
    String getHelloWorld(@RequestHeader(defaultValue = "auth") String auth);
}
