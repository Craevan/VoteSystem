package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.to.DishTo;
import com.crevan.votesystem.web.MatcherFactory;

import java.time.LocalDate;

import static com.crevan.votesystem.web.restaurant.RestaurantTestData.*;

public class DishTestData {

    public static final int NOT_EXISTED_DISH_ID = 0;

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);

    public static final Dish kriekDish1 = new Dish(1, "kriek dish 1", 10000L, kriek, LocalDate.of(2023, 5, 1));
    public static final Dish kriekDish2 = new Dish(2, "kriek dish 2", 20000L, kriek, LocalDate.of(2023, 5, 1));
    public static final Dish altVelvetDish1 = new Dish(3, "altVelvet dish 1", 11000L, altVelvet, LocalDate.of(2023, 5, 1));
    public static final Dish altVelvetDish2 = new Dish(4, "altVelvet dish 2", 21000L, altVelvet, LocalDate.of(2023, 5, 1));
    public static final Dish paulanerDish1 = new Dish(5, "paulaner dish 1", 12000L, paulaner, LocalDate.of(2023, 5, 1));
    public static final Dish paulanerDish2 = new Dish(6, "paulaner dish 2", 22000L, paulaner, LocalDate.of(2023, 5, 1));
    public static final Dish salhinoDish1 = new Dish(7, "salhino dish 1", 13000L, salhino, LocalDate.of(2023, 5, 1));
    public static final Dish salhinoDish2 = new Dish(8, "salhino dish 2", 23000L, salhino, LocalDate.of(2023, 5, 1));

    public static final DishTo newTo = new DishTo(null, "newDishTo", 1L, LocalDate.now(), KRIEK_ID);
}
