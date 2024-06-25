package com.motorny.client;

import com.motorny.config.FeignConfig;
import com.motorny.dto.UserDto;
import com.motorny.dto.UserDtoFromSecondService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "second-service",
        url = "${second.service.config.url}",
        configuration = FeignConfig.class
)
public interface FirstServiceClient {

    @GetMapping("/api/hello-world")
    String getHelloWorld(@RequestHeader() String auth);

    @GetMapping("/api/users")
    List<UserDtoFromSecondService> getAllUsers(@RequestHeader() String auth);
}
