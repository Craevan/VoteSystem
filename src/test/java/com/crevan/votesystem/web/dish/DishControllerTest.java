package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.to.DishTo;
import com.crevan.votesystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static com.crevan.votesystem.util.DishUtil.getTos;
import static com.crevan.votesystem.web.MatcherFactory.usingIgnoringFieldsComparator;
import static com.crevan.votesystem.web.dish.DishController.REST_URL;
import static com.crevan.votesystem.web.dish.DishTestData.*;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.*;
import static com.crevan.votesystem.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends AbstractControllerTest {

    static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        LocalDate date = LocalDate.now();
        List<DishTo> kriekDishTos = getTos(List.of(kriekDish1, kriekDish2));
        List<DishTo> altDishTos = getTos(List.of(altVelvetDish1, altVelvetDish2));
        List<DishTo> paulanerDishTos = getTos(List.of(paulanerDish1, paulanerDish2));
        List<DishTo> salhinoDishTos = getTos(List.of(salhinoDish1, salhinoDish2));
//        For restaurant Kriek
        perform(MockMvcRequestBuilders.get(REST_URL + "?restaurantId=" + kriek.id() + "&date=" + date))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(DishTo.class).contentJson(kriekDishTos));
//        For restaurant Alt Velvet
        perform(MockMvcRequestBuilders.get(REST_URL + "?restaurantId=" + altVelvet.id() + "&date=" + date))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(DishTo.class).contentJson(altDishTos));
//        For restaurant Paulaner
        perform(MockMvcRequestBuilders.get(REST_URL + "?restaurantId=" + paulaner.id() + "&date=" + date))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(DishTo.class).contentJson(paulanerDishTos));
//        For restaurant Salhino
        perform(MockMvcRequestBuilders.get(REST_URL + "?restaurantId=" + salhino.id() + "&date=" + date))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(DishTo.class).contentJson(salhinoDishTos));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + kriekDish1.id()))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(Dish.class, "restaurant").contentJson(kriekDish1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}