package com.crevan.votesystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Access(AccessType.FIELD)
@Table(name = "restaurant", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// https://www.baeldung.com/spring-data-jpa-named-entity-graphs
@NamedEntityGraph(name = "menu", attributeNodes = @NamedAttributeNode("menu"))
public class Restaurant extends NamedEntity implements Serializable {

    @JsonManagedReference
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @Hidden
    private Set<Dish> menu;

    public Restaurant(final Restaurant restaurant) {
        this(restaurant.id(), restaurant.name, restaurant.menu);
    }

    public Restaurant(final Integer id, final String name) {
        super(id, name);
    }

    public Restaurant(final Integer id, final String name, final Set<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }
}
