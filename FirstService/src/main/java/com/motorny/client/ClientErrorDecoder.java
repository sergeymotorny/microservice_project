package com.motorny.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motorny.config.CustomFeignException;
import com.motorny.config.ExceptionMessage;
import com.motorny.exception.BadRequestException;
import com.motorny.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class ClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

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
                return new BadRequestException(message.getMessage() != null ? message.getMessage() : "BadRequest!");
            case 404:
                return new NotFoundException(message.getMessage() != null ? message.getMessage() : "NotFound!");
            case 500:
                return new CustomFeignException(message.getMessage() != null ? message.getMessage() : "InternalServerError!");
            default:
                return errorDecoder.decode(s, response);
        }
    }
}
