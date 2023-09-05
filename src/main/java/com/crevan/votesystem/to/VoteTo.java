package com.crevan.votesystem.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
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

    @Min(0)
    private int restaurantId;

    @PastOrPresent(message = "you can't vote from future")
    private LocalDate voteDate;

    public VoteTo(final Integer id, final int restaurantId, final LocalDate voteDate) {
        super(id);
        this.restaurantId = restaurantId;
        this.voteDate = voteDate;
    }
}
