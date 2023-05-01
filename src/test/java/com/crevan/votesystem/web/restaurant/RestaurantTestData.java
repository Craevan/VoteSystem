package com.crevan.votesystem.web.restaurant;

import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.web.MatcherFactory;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");

    public static final int KRIEK_ID = 1;
    public static final int ALTVELVET_ID = 2;
    public static final int PAULANER_ID = 3;
    public static final int SALHINO_ID = 4;
    public static final int NOT_FOUND = 0;

    public static Restaurant kriek = new Restaurant(KRIEK_ID, "Kriek");
    public static Restaurant altVelvet = new Restaurant(ALTVELVET_ID, "AltVelvet");
    public static Restaurant paulaner = new Restaurant(PAULANER_ID, "Paulaner");
    public static Restaurant salhino = new Restaurant(SALHINO_ID, "Salhino");

    public static Restaurant getNew() {
        return new Restaurant(null, "NEW_TEST_RESTAURANT");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(KRIEK_ID, "KRIEK_UPDATED");
    }
}
