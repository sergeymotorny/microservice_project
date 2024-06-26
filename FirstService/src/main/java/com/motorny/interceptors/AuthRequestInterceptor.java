package com.motorny.interceptors;

import feign.RequestTemplate;

public interface AuthRequestInterceptor {

    void apply(RequestTemplate template);

    Class<?> applyForFeignClientClass();
}
