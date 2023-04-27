package com.crevan.votesystem.web.restaurant;

import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.to.RestaurantTo;
import com.crevan.votesystem.util.RestaurantUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getting all restaurants");
        return RestaurantUtil.getTos(repository.findAll());
    }

    @GetMapping("/{id}")
    public Restaurant getById(@PathVariable final int id) {
        log.info("get restaurant with id={}", id);
        return repository.getExisted(id);
    }
}
