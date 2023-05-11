package com.crevan.votesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "dish")
@ToString(exclude = "restaurant")
@NoArgsConstructor
public class Dish extends NamedEntity {

    @NotNull
    @Column(name = "price")
    private Long price;

    @NotNull
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @Column(name = "vote_date")
    @CreationTimestamp
    private LocalDate voteDate;

    public Dish(final Integer id, final String name, final Long price, final Restaurant restaurant, final LocalDate voteDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }
}
