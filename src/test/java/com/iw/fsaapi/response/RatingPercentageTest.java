package com.iw.fsaapi.response;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class RatingPercentageTest {

    @Test
    public void shouldCreateARatingPercentage() {
        //arrange
        final String rating = "MyRating";
        final double percentage = 45.66;

        //act
        final RatingPercentage ratingPercentage = new RatingPercentage(rating,percentage);

        //assert
        assertThat(ratingPercentage.getRating(),is(rating));
        assertThat(ratingPercentage.getPercentage(),is(percentage));
    }

}