package com.iw.fsaapi.api;

import com.iw.fsaapi.AuthoritiesSupplier;
import com.iw.fsaapi.response.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthoritiesController {

    private final AuthoritiesSupplier authoritiesSupplier;

    @Autowired
    public AuthoritiesController(final AuthoritiesSupplier authoritiesSupplier) {
        this.authoritiesSupplier = authoritiesSupplier;
    }

    @GetMapping("/authorities")
    public HttpEntity<Authorities> getAuthorities() {
        return null;
    }

}
