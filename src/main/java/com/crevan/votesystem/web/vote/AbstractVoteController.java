package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.repository.RestaurantRepository;
import com.crevan.votesystem.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractVoteController {

    @Autowired
    protected VoteRepository voteRepository;

    @Autowired
    protected RestaurantRepository restaurantRepository;

    protected Restaurant getRestaurant(final int id) {
        return restaurantRepository.getExisted(id);
    }
}
