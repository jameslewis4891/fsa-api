package com.iw.fsaapi.adapter;

import com.iw.fsaapi.configuration.FSAConfiguration;
import com.iw.fsaapi.response.Authority;
import com.iw.fsaapi.response.Establishment;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EstablishmentsByAuthorityIdTest {

    @Mock
    private EstablishmentsResponseToEstablishments establishmentsResponseToEstablishments;
    @Mock
    private FSAConfiguration fsaConfiguration;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private AuthoritySupplier authoritySupplier;
    @InjectMocks
    private EstablishmentsByAuthorityId establishmentsByAuthorityId;

    @Test
    public void shouldRetrieveEstablishmentsFromAuthorityId() {
        //arrange
        final Integer authorityId = 234324;
        final Authority authority = new Authority(authorityId,"TestAuthority");
        final String fsaBaseEndpoint = "http://localhost:8008/fsa";
        final List<Establishment> establishmentList = new ArrayList<>();
        final ResponseEntity responseEntity = mock(ResponseEntity.class);

        when(establishmentsResponseToEstablishments.apply(responseEntity)).thenReturn(establishmentList);
        when(restTemplate.exchange(anyString(),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class))).thenReturn(responseEntity);
        when(fsaConfiguration.getEndPoint()).thenReturn(fsaBaseEndpoint);
        when(authoritySupplier.apply(authorityId)).thenReturn(authority);

        //act
        final List<Establishment> establishmentListResult = establishmentsByAuthorityId.apply(authorityId);

        //assert
        assertThat(establishmentListResult,is(establishmentList));
        verify(restTemplate).exchange(eq(fsaBaseEndpoint+"/Establishments?localAuthorityId="+authorityId+"&pageSize=0"),eq(HttpMethod.GET),any(HttpEntity.class),eq(String.class));
        verify(authoritySupplier).apply(authorityId);
        verify(fsaConfiguration).getEndPoint();
    }
}