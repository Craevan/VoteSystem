package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.to.DishTo;
import com.crevan.votesystem.util.DishUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@CacheConfig(cacheNames = "dishes")
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController extends AbstractDishController {

    public static final String REST_URL = "/api/dish";

    @GetMapping
    @Cacheable
    public List<DishTo> getAll(@RequestParam final int restaurantId, @RequestParam final LocalDate date) {
        log.info("getting all dishes for restaurant with id={}", restaurantId);
        return DishUtil.getTos(dishRepository.findAll(restaurantId, date));
    }

    @GetMapping("/{id}")
    public Dish getById(@PathVariable final int id) {
        log.info("get dish with id={}", id);
        return dishRepository.getExisted(id);
    }
}
