package com.motorny.interceptors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InterceptorProvider {

    private final List<AuthRequestInterceptor> requestInterceptors;

    public AuthRequestInterceptor getInterceptorOfClientType(Class<?> clazz) {
        return requestInterceptors.stream()
                .filter(interceptor -> clazz.equals(interceptor.applyForFeignClientClass()))
                .findFirst()
                .get();
    }
}
