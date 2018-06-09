package com.iw.fsaapi.adapter;

import com.iw.fsaapi.response.RatingPercentage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RatingPercentageFromAuthorityId implements Function<Integer,List<RatingPercentage>> {

    private static final Logger logger = LoggerFactory.getLogger(RatingPercentageFromAuthorityId.class);

    private final EstablishmentsByAuthorityId establishmentsByAuthorityId;
    private final EstablishmentsToRatingPercentage establishmentsToRatingPercentage;

    @Autowired
    public RatingPercentageFromAuthorityId(final EstablishmentsByAuthorityId establishmentsByAuthorityId,
                                           final EstablishmentsToRatingPercentage establishmentsToRatingPercentage) {
        this.establishmentsByAuthorityId = establishmentsByAuthorityId;
        this.establishmentsToRatingPercentage = establishmentsToRatingPercentage;
    }

    @Override
    public List<RatingPercentage> apply(final Integer authorityId) {
        logger.info("Obtaining establishment percentage rating from authority {}",authorityId);
        return establishmentsToRatingPercentage.apply(establishmentsByAuthorityId.apply(authorityId));
    }
}
