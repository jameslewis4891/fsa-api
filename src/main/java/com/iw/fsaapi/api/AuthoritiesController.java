package com.iw.fsaapi.api;

import com.iw.fsaapi.adapter.AuthoritiesSupplier;
import com.iw.fsaapi.response.Authorities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthoritiesController {

    private static final Logger logger = LoggerFactory.getLogger(AuthoritiesController.class);

    private final AuthoritiesSupplier authoritiesSupplier;

    @Autowired
    public AuthoritiesController(final AuthoritiesSupplier authoritiesSupplier) {
        this.authoritiesSupplier = authoritiesSupplier;
    }

    @GetMapping("/authorities")
    public HttpEntity<Authorities> getAuthorities() {
        logger.info("GET Authorities");
        return ResponseEntity.ok(authoritiesSupplier.get());
    }

}
