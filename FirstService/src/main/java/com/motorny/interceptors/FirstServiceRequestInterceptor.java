package com.motorny.interceptors;

import com.motorny.client.FirstServiceClient;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FirstServiceRequestInterceptor implements AuthRequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("auth", "let_me_in");
    }

    @Override
    public Class<?> applyForFeignClientClass() {
        return FirstServiceClient.class;
    }
}
