package com.iw.fsaapi.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iw.fsaapi.response.Establishment;
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
class EstablishmentsResponseToEstablishments implements Function<ResponseEntity<String>, List<Establishment>> {

    private static final Logger logger = LoggerFactory.getLogger(EstablishmentsResponseToEstablishments.class);

    private final ObjectMapper objectMapper;

    @Autowired
    EstablishmentsResponseToEstablishments(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Establishment> apply(final ResponseEntity<String> responseEntity) {

        final List<Establishment> establishmentList = new ArrayList<>();

        logger.info("Converting establishments response from FSA into list of establishments");
        try {
            final JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());

            jsonNode.get("establishments").forEach(node ->
                    establishmentList.add(new Establishment(node.get("BusinessName").asText(),node.get("RatingValue").asText())));

        } catch (IOException | NullPointerException e) {
            logger.error("Unable to convert establishments response from FSA",e);
            throw new IllegalStateException("Unable to convert establishments response from FSA",e);
        }

        return establishmentList;
    }
}
