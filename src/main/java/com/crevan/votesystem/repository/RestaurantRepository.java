package com.crevan.votesystem.repository;

import com.crevan.votesystem.model.Restaurant;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
