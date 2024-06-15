package com.motorny.security;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RequestHeaderAuthenticationProvider implements AuthenticationProvider {

    @Value("${auth.secret}")
    private String apiAuthSecret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String authSecretKey = String.valueOf(authentication.getPrincipal());

        if (Strings.isBlank(authSecretKey) || !authSecretKey.equals(apiAuthSecret)) {
            throw new BadCredentialsException("Bad Request Header Credentials!");
        }


        // todo: переделать, убрав null
        return new PreAuthenticatedAuthenticationToken(
                authentication.getPrincipal(), null, new ArrayList<>()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(PreAuthenticatedAuthenticationToken.class);
    }
}
