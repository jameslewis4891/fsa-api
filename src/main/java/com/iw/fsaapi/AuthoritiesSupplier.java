package com.iw.fsaapi;

import com.iw.fsaapi.response.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.function.Supplier;

@Component
public class AuthoritiesSupplier implements Supplier<Authorities> {

    private final RestTemplate restTemplate;

    @Autowired
    public AuthoritiesSupplier(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Authorities get() {
        return null;
    }
}
