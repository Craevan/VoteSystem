package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.to.DishTo;
import com.crevan.votesystem.util.DishUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Dishes", description = "Controller for Users")
@CacheConfig(cacheNames = "dishes")
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@ApiResponse(responseCode = "401", description = "Unauthorized")
public class DishController extends AbstractDishController {

    static final String REST_URL = "/api/dishes";

    @Override
    @GetMapping
    @Cacheable
    @Operation(description = "Get menu for restaurant by ID and date",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<DishTo> getAll(@RequestParam final int restaurantId, @RequestParam final LocalDate date) {
        return super.getAll(restaurantId, date);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(description = "Get dish by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public Dish getById(@PathVariable final int id) {
        return super.getById(id);
    }
}
