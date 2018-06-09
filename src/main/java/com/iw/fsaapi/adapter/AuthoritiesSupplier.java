package com.iw.fsaapi.adapter;

import com.iw.fsaapi.configuration.FSAConfiguration;
import com.iw.fsaapi.response.Authorities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.function.Supplier;

@Component
public class AuthoritiesSupplier implements Supplier<Authorities> {

    private static final Logger logger = LoggerFactory.getLogger(AuthoritiesSupplier.class);

    private final RestTemplate restTemplate;
    private final FSAConfiguration fsaConfiguration;
    private final AuthoritiesResponseToAuthorities authoritiesResponseToAuthorities;

    @Autowired
    public AuthoritiesSupplier(final RestTemplate restTemplate,
                               final FSAConfiguration fsaConfiguration,
                               final AuthoritiesResponseToAuthorities authoritiesResponseToAuthorities) {
        this.restTemplate = restTemplate;
        this.fsaConfiguration = fsaConfiguration;
        this.authoritiesResponseToAuthorities = authoritiesResponseToAuthorities;
    }


    @Override
    public Authorities get() {
        logger.info("Supplying list of authorities");
        return new Authorities(authoritiesResponseToAuthorities.apply(executeRequest()));
    }

    private ResponseEntity<String> executeRequest() {
        final String url = fsaConfiguration.getEndPoint()+"/Authorities/basic";

        logger.info("Calling {}",url);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("x-api-version","2");
        final HttpEntity httpEntity = new HttpEntity("",httpHeaders);

        return restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
    }
}
