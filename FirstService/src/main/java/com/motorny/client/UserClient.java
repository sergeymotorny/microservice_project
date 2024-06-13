package com.motorny.client;

import com.motorny.configuration.FeignClientConfig;
import com.motorny.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "SecondService",
        url = "${second.service.config.url}",
        configuration = FeignClientConfig.class
)

public interface UserClient {

    @GetMapping("/user/{id}")
    User getUserById(@PathVariable("id") Long id);

    @GetMapping("/hello-world")
    String getHelloWorld();
}
