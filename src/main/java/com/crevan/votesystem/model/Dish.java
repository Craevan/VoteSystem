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
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "menu_date"}, name = "unique_name_date_idx"))
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

    @Column(name = "menu_date")
    private LocalDate menuDate;

    public Dish(final Integer id, final String name, final Long price, final Restaurant restaurant, final LocalDate menuDate) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
        this.menuDate = menuDate;
    }
}
