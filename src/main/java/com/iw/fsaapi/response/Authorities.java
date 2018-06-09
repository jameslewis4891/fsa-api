package com.iw.fsaapi.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

public class Authorities {

    private Integer count;

    @JsonUnwrapped
    private List<Authority> authorities;

    public Authorities(final List<Authority> authorities) {
        this.authorities = authorities;
        this.count = authorities.size();
    }

    public Integer getCount() {
        return count;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }
}
