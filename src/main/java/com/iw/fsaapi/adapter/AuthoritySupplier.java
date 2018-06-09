package com.iw.fsaapi.adapter;

import com.iw.fsaapi.configuration.FSAConfiguration;
import com.iw.fsaapi.response.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.function.Function;

@Component
public class AuthoritySupplier implements Function<Integer,Authority> {

    private static final Logger logger = LoggerFactory.getLogger(AuthoritySupplier.class);

    private final FSAConfiguration fsaConfiguration;
    private final RestTemplate restTemplate;
    private final AuthorityResponseToAuthority authorityResponseToAuthority;

    @Autowired
    public AuthoritySupplier(final FSAConfiguration fsaConfiguration,
                             final RestTemplate restTemplate,
                             final AuthorityResponseToAuthority authorityResponseToAuthority) {
        this.fsaConfiguration = fsaConfiguration;
        this.restTemplate = restTemplate;
        this.authorityResponseToAuthority = authorityResponseToAuthority;
    }

    @Override
    public Authority apply(final Integer authorityId) {
        logger.info("Obtaining authority details for authority id {}",authorityId);
        return authorityResponseToAuthority.apply(executeRequest(authorityId));
    }

    private ResponseEntity<String> executeRequest(final Integer authorityId) {
        final String url = fsaConfiguration.getEndPoint()+"/Authorities/"+authorityId;

        logger.info("Calling {}",url);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("x-api-version","2");
        final HttpEntity httpEntity = new HttpEntity("",httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new IllegalArgumentException(String.format("Authority with id %s could not be found",authorityId));
        }

        return responseEntity;
    }
}
