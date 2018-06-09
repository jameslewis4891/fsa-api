package com.iw.fsaapi.api;

import com.iw.fsaapi.adapter.AuthoritiesSupplier;
import com.iw.fsaapi.response.Authorities;
import com.iw.fsaapi.response.Authority;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthoritiesController.class)
public class AuthoritiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthoritiesSupplier authoritiesSupplier;

    @Test
    public void shouldProvideAListOfAuthorities() throws Exception {
        //arrange
        final Integer authorityId = 232;
        final String authorityName = "Authority";
        final List<Authority> authorityList = new ArrayList();
        final Authority authority = new Authority(authorityId,authorityName);
        authorityList.add(authority);

        final Authorities authorities = new Authorities(authorityList);

        when(authoritiesSupplier.get()).thenReturn(authorities);

        final String expectedBody = "{\"count\":1,\"authorities\":[{\"authorityId\":232,\"name\":\"Authority\"}]}";

        //act
        final MvcResult mvcResult = mockMvc.perform(get("/authorities")).andReturn();

        //assert
        final String bodyResult = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus(),is(HttpStatus.OK.value()));
        assertThat(bodyResult.equals(expectedBody),is(true));
        verify(authoritiesSupplier).get();
    }

}