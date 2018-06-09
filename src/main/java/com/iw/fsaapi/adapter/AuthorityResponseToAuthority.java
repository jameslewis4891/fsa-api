package com.iw.fsaapi.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iw.fsaapi.response.Authority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.function.Function;

@Component
public class AuthorityResponseToAuthority implements Function<ResponseEntity<String>,Authority> {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityResponseToAuthority.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthorityResponseToAuthority(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Authority apply(final ResponseEntity<String> responseEntity) {
        logger.info("Converting authority response to authority");

        try {
            final JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

            return new Authority(jsonNode.get("LocalAuthorityId").asInt(),jsonNode.get("Name").asText());

        } catch (IOException | NullPointerException e) {
            logger.error("Unable to retrieve authority details from authority response",e);
            throw new IllegalStateException("Unable to retrieve authority details from authority response",e);
        }
    }
}
