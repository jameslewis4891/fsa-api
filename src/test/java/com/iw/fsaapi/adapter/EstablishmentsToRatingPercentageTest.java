package com.iw.fsaapi.adapter;

import com.iw.fsaapi.response.Establishment;
import com.iw.fsaapi.response.RatingPercentage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class EstablishmentsToRatingPercentageTest {


    @Test
    public void shouldReturnRatingPercentages() {
        //arrange
        final List<Establishment> establishmentList = new ArrayList();

        establishmentList.add(new Establishment("A","5"));
        establishmentList.add(new Establishment("B","5"));
        establishmentList.add(new Establishment("C","1"));
        establishmentList.add(new Establishment("D","Exempt"));

        //act
        List<RatingPercentage> ratingPercentages = new EstablishmentsToRatingPercentage().apply(establishmentList);

        //assert
        assertThat(ratingPercentages.size(),is(3));
        assertThat(ratingPercentages.stream().filter(i -> i.getRating().equals("5")).findFirst().get().getPercentage(),is(50.0));
        assertThat(ratingPercentages.stream().filter(i -> i.getRating().equals("1")).findFirst().get().getPercentage(),is(25.0));
        assertThat(ratingPercentages.stream().filter(i -> i.getRating().equals("Exempt")).findFirst().get().getPercentage(),is(25.0));
    }

}