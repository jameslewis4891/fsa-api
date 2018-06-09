package com.iw.fsaapi.response;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class AuthoritiesTest {

    @Test
    public void shouldCreateAuthorities() {
        //arrange
        final List<Authority> authorityList = new ArrayList();
        final Authority authority = mock(Authority.class);
        authorityList.add(authority);

        //act
        final Authorities authorities = new Authorities(authorityList);

        //assert
        assertThat(authorities.getAuthorities(),is(authorityList));
        assertThat(authorities.getCount(),is(1));
    }

}