package com.crevan.votesystem.repository;

import com.crevan.votesystem.model.Dish;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    List<Dish> findAll(final int restaurantId, final LocalDate date);
}
