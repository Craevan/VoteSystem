package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.repository.DishRepository;
import com.crevan.votesystem.to.DishTo;
import com.crevan.votesystem.util.DishUtil;
import com.crevan.votesystem.util.json.JsonUtil;
import com.crevan.votesystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static com.crevan.votesystem.web.dish.AdminDishController.REST_URL;
import static com.crevan.votesystem.web.dish.DishTestData.*;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.KRIEK_ID;
import static com.crevan.votesystem.web.user.UserTestData.ADMIN_MAIL;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminDishControllerTest extends AbstractControllerTest {

    static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());
        Dish created = DISH_MATCHER.readFromJson(action);
        Dish newDish = DishUtil.createNewFromTo(newTo);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(new DishTo())))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo updated = new DishTo(1, "updated", 1L, LocalDate.of(2023, 5, 1), KRIEK_ID);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + updated.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        DishTo dishTo = new DishTo(0, null, null, null, null);
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + dishTo.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(dishTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + kriekDish1.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(kriekDish1)))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.findById(kriekDish1.id()).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + NOT_EXISTED_DISH_ID))
                .andExpect(status().isNotFound());
    }
}
