package com.iw.fsaapi.response;

public class Establishment {

    private String businessName;
    private String ratingValue;

    public Establishment(final String businessName,
                         final String ratingValue) {
        this.businessName = businessName;
        this.ratingValue = ratingValue;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getRatingValue() {
        return ratingValue;
    }
}
