package com.iw.fsaapi.api;

import com.iw.fsaapi.adapter.RatingPercentageFromAuthorityId;
import com.iw.fsaapi.response.RatingPercentage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/establishments")
public class EstablishmentsController {

    private static final Logger logger = LoggerFactory.getLogger(EstablishmentsController.class);

    private final RatingPercentageFromAuthorityId ratingPercentageFromAuthorityId;

    @Autowired
    public EstablishmentsController(final RatingPercentageFromAuthorityId ratingPercentageFromAuthorityId) {
        this.ratingPercentageFromAuthorityId = ratingPercentageFromAuthorityId;
    }

    @GetMapping("/ratings/byAuthority/{authorityId}")
    public HttpEntity<List<RatingPercentage>> getEstablishmentRatingsByAuthority(@PathVariable("authorityId") final int authorityId) {
        logger.info("GET Establishment rating by authority id");
        return ResponseEntity.ok(ratingPercentageFromAuthorityId.apply(authorityId));
    }


}
