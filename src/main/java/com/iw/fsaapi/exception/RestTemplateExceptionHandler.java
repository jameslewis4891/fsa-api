package com.iw.fsaapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateExceptionHandler implements ResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateExceptionHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return !clientHttpResponse.getStatusCode().is2xxSuccessful() && clientHttpResponse.getStatusCode() != HttpStatus.NOT_FOUND;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        logger.error("Unexpected status code {} with body {} returned",clientHttpResponse.getStatusCode(),clientHttpResponse.getBody());
        throw new IllegalStateException(String.format("Invalid http response code %s",clientHttpResponse.getRawStatusCode()));
    }
}
