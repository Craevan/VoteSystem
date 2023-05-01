package com.crevan.votesystem.web.restaurant;

import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.repository.RestaurantRepository;
import com.crevan.votesystem.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    public Restaurant create(final Restaurant restaurant) {
        log.info("creating {}", restaurant);
        return repository.save(restaurant);
    }

    public void update(final Restaurant restaurant, final int id) {
        log.info("updating {} with id={}", restaurant, id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    public void delete(final int id) {
        log.info("deleting restaurant with id={}", id);
        repository.deleteExisted(id);
    }
}
