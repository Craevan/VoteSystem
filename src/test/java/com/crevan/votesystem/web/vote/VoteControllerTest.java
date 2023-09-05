package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.util.json.JsonUtil;
import com.crevan.votesystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.util.List;

import static com.crevan.votesystem.util.VoteUtil.getTos;
import static com.crevan.votesystem.web.MatcherFactory.usingIgnoringFieldsComparator;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.KRIEK_ID;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.PAULANER_ID;
import static com.crevan.votesystem.web.user.UserTestData.TEST_USER_MAIL;
import static com.crevan.votesystem.web.user.UserTestData.USER_MAIL;
import static com.crevan.votesystem.web.vote.VoteController.REST_URL;
import static com.crevan.votesystem.web.vote.VoteTestData.*;
import static org.mockito.Mockito.mockStatic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    private static final String RESTAURANT_ID_PARAM = "?restaurantId=";

    private static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        List<VoteTo> voteTos = getTos(List.of(vote_1, vote_2, vote_3));
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(VoteTo.class).contentJson(voteTos));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + vote_1.getId()))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(Vote.class, "user", "restaurant").contentJson(vote_1));
    }

    @Test
    @WithUserDetails(value = TEST_USER_MAIL)
    void createWithLocation() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID_PARAM + PAULANER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newTo)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createInvalid() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidTo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateFalse() throws Exception {
//        https://github.com/mockito/mockito/issues/2676
        try (MockedStatic<LocalTime> guid1 = mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
            guid1.when(LocalTime::now).thenReturn(afterTime);
            perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_PARAM + KRIEK_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updatedTo)))
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateTrue() throws Exception {
        try (MockedStatic<LocalTime> guid1 = mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
            guid1.when(LocalTime::now).thenReturn(beforeTime);
            perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID_PARAM + KRIEK_ID)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updatedTo)))
                    .andExpect(status().isNoContent());
        }
    }
}
