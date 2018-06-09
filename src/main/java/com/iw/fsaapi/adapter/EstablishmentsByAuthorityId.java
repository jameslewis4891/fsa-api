package com.iw.fsaapi.adapter;

import com.iw.fsaapi.configuration.FSAConfiguration;
import com.iw.fsaapi.response.Establishment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Component
public class EstablishmentsByAuthorityId implements Function<Integer,List<Establishment>> {

    private static final Logger logger = LoggerFactory.getLogger(EstablishmentsByAuthorityId.class);

    private final EstablishmentsResponseToEstablishments establishmentsResponseToEstablishments;
    private final FSAConfiguration fsaConfiguration;
    private final RestTemplate restTemplate;

    @Autowired
    public EstablishmentsByAuthorityId(final EstablishmentsResponseToEstablishments establishmentsResponseToEstablishments,
                                       final FSAConfiguration fsaConfiguration,
                                       final RestTemplate restTemplate) {
        this.establishmentsResponseToEstablishments = establishmentsResponseToEstablishments;
        this.fsaConfiguration = fsaConfiguration;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Establishment> apply(final Integer authorityId) {
        //TODO Implement
        logger.info("Getting establishments for authority {}",authorityId);
        return establishmentsResponseToEstablishments.apply(executeRequest(authorityId));
    }

    private ResponseEntity<String> executeRequest(final Integer authorityId) {
        final String url = String.format("%s/Establishments?localAuthorityId=%s&pageSize=0",fsaConfiguration.getEndPoint(),authorityId);

        logger.info("Calling {}",url);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("x-api-version","2");
        final HttpEntity httpEntity = new HttpEntity("",httpHeaders);

        return restTemplate.exchange(url,HttpMethod.GET,httpEntity,String.class);
    }
}
