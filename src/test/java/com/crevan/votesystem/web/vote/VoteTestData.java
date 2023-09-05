package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;

import static com.crevan.votesystem.web.restaurant.RestaurantTestData.*;
import static com.crevan.votesystem.web.user.UserTestData.user;

public class VoteTestData {
    public static final Vote vote_1 = new Vote(1, user, kriek, LocalDate.of(2023, 5, 1));
    public static final Vote vote_2 = new Vote(2, user, altVelvet, LocalDate.of(2023, 5, 2));
    public static final Vote vote_3 = new Vote(3, user, salhino, LocalDate.now());
    public static final VoteTo newTo = new VoteTo(null, PAULANER_ID, null);
    public static final VoteTo invalidTo = new VoteTo(null, 0, LocalDate.now().plusDays(1));
    public static final VoteTo updatedTo = new VoteTo(null, PAULANER_ID, LocalDate.now());

    public static final LocalTime afterTime = LocalTime.of(11, 0, 1);
    public static final LocalTime beforeTime = LocalTime.of(10, 59, 59);
}
