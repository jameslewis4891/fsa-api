package com.iw.fsaapi.adapter;

import com.iw.fsaapi.response.Establishment;
import com.iw.fsaapi.response.RatingPercentage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingPercentageFromAuthorityIdTest {

    @Mock
    private EstablishmentsByAuthorityId establishmentsByAuthorityId;
    @Mock
    private EstablishmentsToRatingPercentage establishmentsToRatingPercentage;
    @InjectMocks
    private RatingPercentageFromAuthorityId ratingPercentageFromAuthorityId;

    @Test
    public void shouldObtainPercentageRatingsFromAuthorityId() {
        //arrange
        final Integer authorityId = 3434;
        final List<Establishment> establishmentList = new ArrayList();
        final List<RatingPercentage> ratingPercentageList = new ArrayList<>();

        when(establishmentsByAuthorityId.apply(authorityId)).thenReturn(establishmentList);
        when(establishmentsToRatingPercentage.apply(establishmentList)).thenReturn(ratingPercentageList);

        //act
        final List<RatingPercentage> ratingPercentagesResult = ratingPercentageFromAuthorityId.apply(authorityId);

        //assert
        assertThat(ratingPercentagesResult,is(ratingPercentageList));
        verify(establishmentsByAuthorityId).apply(authorityId);
        verify(establishmentsToRatingPercentage).apply(establishmentList);
    }


}