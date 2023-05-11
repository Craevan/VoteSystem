package com.crevan.votesystem.to;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Min(value = 1, message = "Price must be at least 1")
    private Long price;

    @NotNull
    private LocalDate date;

    @NotNull
    private Integer restaurantId;

    public DishTo(final Integer id, final String name, final Long price, final LocalDate date, final Integer restaurantId) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurantId = restaurantId;
    }
}
