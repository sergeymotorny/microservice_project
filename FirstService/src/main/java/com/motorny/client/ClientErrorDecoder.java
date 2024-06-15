package com.motorny.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motorny.exception.*;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Optional;


public class ClientErrorDecoder extends ErrorDecoder.Default {

    @Override
    public Exception decode(String methodKey, Response response) {
        ExceptionMessage message;

        if (response.body() == null) {
            message = getExceptionMessage(methodKey, response);

        } else {
            try (InputStream inputStream = response.body()
                    .asInputStream()) {

                ObjectMapper mapper = new ObjectMapper();
                message = mapper.readValue(inputStream, ExceptionMessage.class);
            } catch (IOException e) {
                return new Exception(e.getMessage());
            }
        }

        return new CustomFeignException(Optional.ofNullable(message.message()).orElse("CustomFeignException!"));
    }

    private ExceptionMessage getExceptionMessage(String methodKey, Response response) {
        ExceptionMessage message;
        message = new ExceptionMessage(
                LocalDateTime.now().toString(),
                response.status(),
                methodKey,
                response.status() + ", " + methodKey,
                response.toString());
        return message;
    }
}
