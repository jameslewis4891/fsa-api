package com.iw.fsaapi.api;

import com.iw.fsaapi.adapter.RatingPercentageFromAuthorityId;
import com.iw.fsaapi.response.Authorities;
import com.iw.fsaapi.response.Authority;
import com.iw.fsaapi.response.RatingPercentage;
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
@WebMvcTest(EstablishmentsController.class)
public class EstablishmentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RatingPercentageFromAuthorityId percentageFromAuthorityId;

    @Test
    public void shouldProvideAListOfEstablishmentRatingByPercentageFromAuthorityId() throws Exception {
        //arrange
        final Integer authorityId = 2345;
        final List<RatingPercentage> ratingPercentageList = new ArrayList();
        final RatingPercentage ratingPercentage = new RatingPercentage("Rating",12.33);
        ratingPercentageList.add(ratingPercentage);

        when(percentageFromAuthorityId.apply(authorityId)).thenReturn(ratingPercentageList);

        final String expectedBody = "[{\"rating\":\"Rating\",\"percentage\":12.33}]";

        //act
        final MvcResult mvcResult = mockMvc.perform(get("/establishments/ratings/byAuthority/"+authorityId)).andReturn();

        //assert
        final String bodyResult = mvcResult.getResponse().getContentAsString();
        assertThat(mvcResult.getResponse().getStatus(),is(HttpStatus.OK.value()));
        assertThat(bodyResult.equals(expectedBody),is(true));
        verify(percentageFromAuthorityId).apply(authorityId);
    }
}