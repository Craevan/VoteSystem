package com.crevan.votesystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString(exclude = "menu")
@Access(AccessType.FIELD)
@Table(name = "restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements Serializable {

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Dish> menu;

    public Restaurant(final Restaurant kriek) {
        this(kriek.id(), kriek.name, kriek.menu);
    }

    public Restaurant(final Integer id, final String name) {
        super(id, name);
    }

    public Restaurant(final Integer id, final String name, final Set<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }
}
