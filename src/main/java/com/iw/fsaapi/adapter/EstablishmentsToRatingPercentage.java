package com.iw.fsaapi.adapter;

import com.iw.fsaapi.response.Establishment;
import com.iw.fsaapi.response.RatingPercentage;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EstablishmentsToRatingPercentage implements Function<List<Establishment>,List<RatingPercentage>> {

    @Override
    public List<RatingPercentage> apply(final List<Establishment> establishments) {

        final DecimalFormat decimalFormat = new DecimalFormat("#.00");

        final List<RatingPercentage> ratingPercentageList = new ArrayList<>();

        final Map<String, Long> countByRatingMap = establishments.stream().collect(Collectors.groupingBy(Establishment::getRatingValue, Collectors.counting()));

        countByRatingMap.forEach((rating, count) -> {
            double ratingPercentage = count * 100.0f / establishments.size();
            ratingPercentageList.add(new RatingPercentage(rating,Double.valueOf(decimalFormat.format(ratingPercentage))));
        });

        return ratingPercentageList;


    }
}
