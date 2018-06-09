package com.iw.fsaapi.adapter;

import com.iw.fsaapi.response.RatingPercentage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RatingPercentageFromAuthorityId implements Function<Integer,List<RatingPercentage>> {

    private static final Logger logger = LoggerFactory.getLogger(RatingPercentageFromAuthorityId.class);

    @Override
    public List<RatingPercentage> apply(final Integer authorityId) {
        return null;
    }
}
