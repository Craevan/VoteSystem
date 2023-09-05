package com.crevan.votesystem.util;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.to.DishTo;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(final DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getPrice(), null, dishTo.getMenuDate());
    }

    public static DishTo asTo(final Dish dish) {
        return new DishTo(dish.id(), dish.getName(), dish.getPrice(), dish.getMenuDate(), dish.getRestaurant().id());
    }

    public static List<DishTo> getTos(final Collection<Dish> dishes) {
        return dishes.stream()
                .map(DishUtil::asTo)
                .collect(Collectors.toList());
    }
}
