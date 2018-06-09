package com.iw.fsaapi.adapter;

import com.iw.fsaapi.configuration.FSAConfiguration;
import com.iw.fsaapi.response.Authorities;
import com.iw.fsaapi.response.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthoritiesSupplierTest {

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private FSAConfiguration fsaConfiguration;
    @Mock
    private AuthoritiesResponseToAuthorities authoritiesResponseToAuthorities;
    @InjectMocks
    private AuthoritiesSupplier authoritiesSupplier;

    @Test
    public void shouldSupplyAuthorities() {
        //arrange
        final String fsaBaseEndpoint = "http://localhost:8008/fsa";
        final List<Authority> authorityList = new ArrayList();

        when(fsaConfiguration.getEndPoint()).thenReturn(fsaBaseEndpoint);


        final ResponseEntity<String> responseEntity = mock(ResponseEntity.class);

        when(restTemplate.exchange(anyString(),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class))).thenReturn(responseEntity);
        when(authoritiesResponseToAuthorities.apply(responseEntity)).thenReturn(authorityList);

        //act
        final Authorities authoritiesResult = authoritiesSupplier.get();

        //assert
        assertThat(authoritiesResult.getAuthorities(),is(authorityList));
        verify(authoritiesResponseToAuthorities).apply(responseEntity);
        verify(restTemplate).exchange(eq(fsaBaseEndpoint+"/Authorities/basic"),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class));
    }
}