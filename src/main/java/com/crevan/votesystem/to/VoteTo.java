package com.crevan.votesystem.to;

import java.time.LocalDate;

public class VoteTo extends BaseEntityTo {

    private Integer restaurantId;

    private LocalDate voteDate;

    public VoteTo(final Integer id, final Integer restaurantId, final LocalDate voteDate) {
        super(id);
        this.restaurantId = restaurantId;
        this.voteDate = voteDate;
    }
}
