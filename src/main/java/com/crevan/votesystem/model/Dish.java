package com.crevan.votesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "dish")
@ToString(exclude = "restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @NotNull
    @Column(name = "price")
    private Long price;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @Column(name = "vote_date")
    private LocalDate voteDate;

    public Dish(final Integer id, final String name, final Long price, final Restaurant restaurant, final LocalDate voteDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }
}
