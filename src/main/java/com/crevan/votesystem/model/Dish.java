package com.crevan.votesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "dish")
@ToString
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
    @ToString.Exclude
    @Hidden
    private Restaurant restaurant;

    @Column(name = "menu_item")
    private LocalDate menuItem;

    public Dish(final Integer id, final String name, final Long price, final Restaurant restaurant, final LocalDate menuItem) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.menuItem = menuItem;
    }
}
