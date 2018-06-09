package com.iw.fsaapi.response;

public class RatingPercentage {

    private final String rating;
    private final double percentage;

    public RatingPercentage(final String rating,
                            final double percentage) {
        this.rating = rating;
        this.percentage = percentage;
    }

    public String getRating() {
        return rating;
    }

    public double getPercentage() {
        return percentage;
    }
}
