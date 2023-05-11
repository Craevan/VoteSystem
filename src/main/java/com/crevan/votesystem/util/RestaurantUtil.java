package com.crevan.votesystem.util;

import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.to.RestaurantTo;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RestaurantUtil {

    public static RestaurantTo asTo(final Restaurant restaurant) {
        return new RestaurantTo(restaurant.id(), restaurant.getName());
    }

    public static List<RestaurantTo> getTos(final Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantUtil::asTo)
                .collect(Collectors.toList());
    }
}
