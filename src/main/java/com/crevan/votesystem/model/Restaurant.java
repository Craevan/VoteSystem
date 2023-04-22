package com.crevan.votesystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Dish> menu;

    public Restaurant(final Integer id, final String name) {
        super(id, name);
    }

    public Restaurant(final Integer id, final String name, final Set<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }
}
