package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.repository.DishRepository;
import com.crevan.votesystem.repository.RestaurantRepository;
import com.crevan.votesystem.to.DishTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import static com.crevan.votesystem.util.DishUtil.createNewFromTo;
import static com.crevan.votesystem.util.validation.ValidationUtil.assureIdConsistent;

@Slf4j
public class AbstractDishController {

    @Autowired
    protected DishRepository dishRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Dish create(final DishTo dishTo) {
        log.info("create {}", dishTo);
        Dish dish = createNewFromTo(dishTo);
        dish.setRestaurant(restaurantRepository.getExisted(dishTo.getRestaurantId()));
        dishRepository.save(dish);
        return dish;
    }

    public void update(final DishTo dishTo, final int id) {
        log.info("update {} with id={}", dishTo, id);
        assureIdConsistent(dishTo, id);
        Dish dish = createNewFromTo(dishTo);
        dish.setRestaurant(restaurantRepository.getExisted(dishTo.getRestaurantId()));
        dishRepository.save(dish);
    }

    public void delete(final int id) {
        log.info("deleting dish with id={}", id);
        dishRepository.deleteExisted(id);
    }
}
