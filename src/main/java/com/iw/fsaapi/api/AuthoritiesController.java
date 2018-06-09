package com.iw.fsaapi.api;

import com.iw.fsaapi.response.Authorities;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthoritiesController {

    @GetMapping("/authorities")
    public HttpEntity<Authorities> getAuthorities() {
        return null;
    }

}
