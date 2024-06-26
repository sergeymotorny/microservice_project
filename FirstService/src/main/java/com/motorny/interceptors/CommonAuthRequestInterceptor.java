package com.motorny.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonAuthRequestInterceptor implements RequestInterceptor {

    private final InterceptorProvider interceptorProvider;

    @Override
    public void apply(RequestTemplate template) {
        AuthRequestInterceptor interceptor =
                interceptorProvider.getInterceptorOfClientType(template.feignTarget().type());
        if (interceptor != null) {
            interceptor.apply(template);
        }
    }
}
