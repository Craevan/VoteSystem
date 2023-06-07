package com.crevan.votesystem.repository;

import com.crevan.votesystem.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    @Query("FROM Dish d WHERE (d.menuItem = :date) and (d.restaurant.id = :restaurantId)")
    List<Dish> findAll(final int restaurantId, final LocalDate date);
}
