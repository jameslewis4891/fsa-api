package com.iw.fsaapi.response;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthorityTest {

    @Test
    public void shouldCreateAuthority() {
        //arrange
        final Integer authorityId = 342;
        final String authorityName = "Authority";

        //act
        final Authority authority = new Authority(authorityId,authorityName);

        //assert
        assertThat(authority.getName(),is(authorityName));
        assertThat(authority.getAuthorityId(),is(authorityId));
    }
}