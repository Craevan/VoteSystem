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

    @Min(value = 1, message = "Price must be at least 1")
    private long price;

    @NotNull
    private LocalDate menuDate;

    @NotNull
    private int restaurantId;

    public DishTo(final Integer id, final String name, final long price, final LocalDate menuDate, final int restaurantId) {
        super(id, name);
        this.price = price;
        this.menuDate = menuDate;
        this.restaurantId = restaurantId;
    }
}
