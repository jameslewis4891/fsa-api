package com.iw.fsaapi.response;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class EstablishmentTest {

    @Test
    public void shouldCreateEstablishment() {
        //arrange
        final String businessName = "businessName";
        final String rating = "5";

        //act
        final Establishment establishment = new Establishment(businessName,rating);

        //assert
        assertThat(establishment.getBusinessName(),is(businessName));
        assertThat(establishment.getRatingValue(),is(rating));
    }

}