package com.crevan.votesystem.repository;

import com.crevan.votesystem.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    // https://www.baeldung.com/spring-data-jpa-named-entity-graphs
    @EntityGraph(value = "menu", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN r.menu m ON m.menuItem = CURRENT_DATE WHERE r.id=?1")
    Optional<Restaurant> getWithMenu(final int id);
}
