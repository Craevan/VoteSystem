package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.to.DishTo;
import com.crevan.votesystem.util.DishUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController extends AbstractDishController {

    public static final String REST_URL = "/api/dish";

    @GetMapping
    public List<DishTo> getAll() {
        log.info("getting all restaurants");
        return DishUtil.getTos(dishRepository.findAll());
    }

    @GetMapping("/{id}")
    public Dish getById(@PathVariable final int id) {
        log.info("get dish with id={}", id);
        return dishRepository.getExisted(id);
    }
}
