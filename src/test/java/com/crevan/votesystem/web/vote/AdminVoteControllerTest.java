package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.web.AbstractControllerTest;
import com.crevan.votesystem.web.restaurant.RestaurantTestData;
import com.crevan.votesystem.web.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.crevan.votesystem.util.VoteUtil.getTos;
import static com.crevan.votesystem.web.MatcherFactory.usingIgnoringFieldsComparator;
import static com.crevan.votesystem.web.restaurant.RestaurantTestData.KRIEK_ID;
import static com.crevan.votesystem.web.user.UserTestData.ADMIN_MAIL;
import static com.crevan.votesystem.web.user.UserTestData.USER_ID;
import static com.crevan.votesystem.web.vote.AdminVoteController.REST_URL;
import static com.crevan.votesystem.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminVoteControllerTest extends AbstractControllerTest {

    static final String REST_URL_SLASH = REST_URL + "/";

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        List<VoteTo> voteTos = getTos(List.of(vote_1, vote_2, vote_3));
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(VoteTo.class).contentJson(voteTos));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByUserId() throws Exception {
        List<VoteTo> voteTos = getTos(List.of(vote_1, vote_2, vote_3));
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-user/" + USER_ID))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(VoteTo.class).contentJson(voteTos));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByNotExistingUserId() throws Exception {
        List<VoteTo> voteTos = Collections.emptyList();
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-user/" + UserTestData.NOT_FOUND))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(VoteTo.class).contentJson(voteTos));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByRestaurant() throws Exception {
        List<VoteTo> voteTos = getTos(List.of(vote_1));
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-restaurant/" + KRIEK_ID))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(VoteTo.class).contentJson(voteTos));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByNotExistingRestaurantId() throws Exception {
        List<VoteTo> voteTos = Collections.emptyList();
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-restaurant/" + RestaurantTestData.NOT_FOUND))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(VoteTo.class).contentJson(voteTos));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getByDate() throws Exception {
        List<VoteTo> voteTos = getTos(List.of(vote_3));
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "by-date/" + LocalDate.now()))
                .andExpect(status().isOk())
                .andExpect(usingIgnoringFieldsComparator(VoteTo.class).contentJson(voteTos));
    }
}
