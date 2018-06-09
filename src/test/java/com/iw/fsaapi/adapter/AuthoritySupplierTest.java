package com.iw.fsaapi.adapter;

import com.iw.fsaapi.configuration.FSAConfiguration;
import com.iw.fsaapi.response.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthoritySupplierTest {

    @Mock
    private FSAConfiguration fsaConfiguration;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private AuthorityResponseToAuthority authorityResponseToAuthority;
    @InjectMocks
    private AuthoritySupplier authoritySupplier;

    @Test
    public void shouldProvideAuthority() {
        //arrange
        final String fsaEndpoint = "http://localhost:8080/fsa";
        final Integer authorityId = 234234;
        final Authority authority = mock(Authority.class);
        final ResponseEntity<String> responseEntity = mock(ResponseEntity.class);

        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(restTemplate.exchange(anyString(),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class))).thenReturn(responseEntity);
        when(fsaConfiguration.getEndPoint()).thenReturn(fsaEndpoint);
        when(authorityResponseToAuthority.apply(responseEntity)).thenReturn(authority);

        //act
        final Authority authorityResult = authoritySupplier.apply(authorityId);

        //assert
        assertThat(authorityResult,is(authority));
        verify(authorityResponseToAuthority).apply(responseEntity);
        verify(restTemplate).exchange(eq(fsaEndpoint+"/Authorities/"+authorityId),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfAuthorityCannotBeFound() {
        //arrange
        final String fsaEndpoint = "http://localhost:8080/fsa";
        final Integer authorityId = 234234;
        final Authority authority = mock(Authority.class);
        final ResponseEntity<String> responseEntity = mock(ResponseEntity.class);

        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
        when(restTemplate.exchange(anyString(),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class))).thenReturn(responseEntity);
        when(fsaConfiguration.getEndPoint()).thenReturn(fsaEndpoint);

        //act
        final Authority authorityResult = authoritySupplier.apply(authorityId);

        //assert
        assertThat(authorityResult,is(authority));
        verifyZeroInteractions(authorityResponseToAuthority);
        verify(restTemplate).exchange(eq(fsaEndpoint+"/Authorities/"+authorityId),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class));
    }

}