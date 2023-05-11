package com.crevan.votesystem.web.dish;

import com.crevan.votesystem.error.SwaggerExceptionInfo;
import com.crevan.votesystem.model.Dish;
import com.crevan.votesystem.to.DishTo;
import com.crevan.votesystem.util.DishUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@ApiResponse(responseCode = "401", description = "Unauthorized",
        content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
public class DishController extends AbstractDishController {

    static final String REST_URL = "/api/dishes";

    @GetMapping
    @Cacheable
    @Operation(description = "Get menu for restaurant by ID and date",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<DishTo> getAll(@RequestParam final int restaurantId, @RequestParam final LocalDate date) {
        log.info("getting all dishes for restaurant with id={}, on date={}", restaurantId, date);
        return DishUtil.getTos(dishRepository.findAll(restaurantId, date));
    }

    @GetMapping("/{id}")
    @Operation(description = "Get dish by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
            })
    public Dish getById(@PathVariable final int id) {
        log.info("get dish with id={}", id);
        return dishRepository.getExisted(id);
    }
}
