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
public class DishTo extends NamedEntityTo {
    private Long price;
    private LocalDate date;
    private Integer restaurantId;

    public DishTo(final Integer id, final String name, final Long price, final LocalDate date, final Integer restaurantId) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurantId = restaurantId;
    }
}
