package com.crevan.votesystem.repository;

import com.crevan.votesystem.model.Dish;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
