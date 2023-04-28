package com.crevan.votesystem.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VoteTo extends BaseEntityTo {

    private Integer restaurantId;

    private LocalDate voteDate;

    public VoteTo(final Integer id, final Integer restaurantId, final LocalDate voteDate) {
        super(id);
        this.restaurantId = restaurantId;
        this.voteDate = voteDate;
    }
}
