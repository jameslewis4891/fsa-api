package com.iw.fsaapi.response;

public class Authority {

    private Integer authorityId;
    private String name;

    public Authority(final Integer authorityId,
                     final String name) {
        this.authorityId = authorityId;
        this.name = name;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public String getName() {
        return name;
    }
}
