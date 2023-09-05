package com.crevan.votesystem.web.restaurant;

import com.crevan.votesystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.crevan.votesystem.web.restaurant.RestaurantController.REST_URL;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.*;
import static com.crevan.votesystem.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String MENU = "menu/";
    static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of(altVelvet, kriek, paulaner, salhino)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + kriek.id()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(kriek));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + kriek.id() + "/" + MENU))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(kriek));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenuNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU + NOT_FOUND))
                .andExpect(status().isNotFound());
    }

    @Test
    void getWithMenuUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + MENU + NOT_FOUND))
                .andExpect(status().isUnauthorized());
    }
}