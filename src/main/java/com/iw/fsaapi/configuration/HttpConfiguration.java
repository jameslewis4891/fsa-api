package com.iw.fsaapi.configuration;

import com.iw.fsaapi.exception.RestTemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfiguration {

    @Autowired
    private RestTemplateExceptionHandler restTemplateExceptionHandler;

    private static final Integer CONNECT_TIMEOUT = 3000;
    private static final Integer SOCKET_TIMEOUT = 30000;

    @Bean
    RestTemplate restTemplate() {
        final RestTemplate restTemplate = new RestTemplateBuilder()
                .setReadTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .build();

        restTemplate.setErrorHandler(restTemplateExceptionHandler);

        return restTemplate;
    }

}
