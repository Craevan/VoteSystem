package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.util.json.JsonUtil;
import com.crevan.votesystem.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.crevan.votesystem.util.VoteUtil.getTos;
import static com.crevan.votesystem.web.MatcherFactory.usingIgnoringFieldsComparator;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.PAULANER_ID;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.SALHINO_ID;
import static com.crevan.votesystem.web.user.UserTestData.USER_MAIL;
import static com.crevan.votesystem.web.vote.VoteController.REST_URL;
import static com.crevan.votesystem.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class VoteControllerTest extends AbstractControllerTest {

    static final String REST_URL_SLASH = REST_URL + "/";

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
    @WithUserDetails(value = USER_MAIL)
    void createWithLocation() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
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
//        https://www.baeldung.com/java-override-system-time
        Clock.fixed(Instant.parse("2023-05-01T11:00:01.00Z"), ZoneId.of(ZoneId.systemDefault().toString()));
        VoteTo updated = new VoteTo(1, PAULANER_ID, LocalDate.of(2023, 5, 1));
        perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=" + updated.getRestaurantId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateTrue() throws Exception {
        Clock.fixed(Instant.parse("2023-05-01T11:59:59.00Z"), ZoneId.of(ZoneId.systemDefault().toString()));
        VoteTo updated = new VoteTo(1, SALHINO_ID, LocalDate.of(2023, 5, 1));
        perform(MockMvcRequestBuilders.put(REST_URL + "?restaurantId=" + updated.getRestaurantId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }
}
