package com.motorny.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motorny.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ClientErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message;

        try (InputStream inputStream = response.body()
                .asInputStream()) {

            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(inputStream, ExceptionMessage.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        switch (response.status()) {
            case 400:
                return new BadRequestException(
                        Optional.ofNullable(message.message()).orElse("BadRequestException!")
                );
            case 401:
                return new UnauthorizedException(
                        Optional.ofNullable(message.message()).orElse("UnauthorizedException!")
                );
            case 404:
                return new NotFoundException(
                        Optional.ofNullable(message.message()).orElse("NotFoundException!")
                );
            case 500:
                return new CustomFeignException(
                        Optional.ofNullable(message.message()).orElse("CustomFeignException!")
                );
            default:
                return super.decode(methodKey, response);
        }
    }
}
