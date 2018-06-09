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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
class AuthoritiesResponseToAuthorities implements Function<ResponseEntity<String>, List<Authority>> {

    private static final Logger logger = LoggerFactory.getLogger(AuthoritiesResponseToAuthorities.class);

    private final ObjectMapper objectMapper;

    @Autowired
    AuthoritiesResponseToAuthorities(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Authority> apply(final ResponseEntity<String> responseEntity) {

        final List<Authority> authorityList = new ArrayList<>();

        logger.info("Converting authorities response from FSA into list of authorities");
        try {
            final JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

            jsonNode.get("authorities").forEach(node ->
                    authorityList.add(new Authority(node.get("LocalAuthorityId").asInt(),node.get("Name").asText())));

        } catch (IOException | NullPointerException e) {
            logger.error("Unable to retrieve authority details from list of authorities",e);
            throw new IllegalStateException("Unable to retrieve authority details from list of authorities",e);
        }

        return authorityList;
    }
}
