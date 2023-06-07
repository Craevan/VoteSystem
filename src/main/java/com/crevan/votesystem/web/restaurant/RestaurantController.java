package com.crevan.votesystem.web.restaurant;

import com.crevan.votesystem.error.NotFoundException;
import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.to.RestaurantTo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CacheConfig(cacheNames = "restaurants")
@Tag(name = "Restaurants-user", description = "Controller for Users")
@ApiResponse(responseCode = "401", description = "Unauthorized")
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    @Cacheable
    @GetMapping
    @Operation(description = "Get all Restaurants",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    @Operation(description = "Get Restaurant by ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public Restaurant getById(@PathVariable final int id) {
        log.info("get restaurant with id={}", id);
        return repository.getExisted(id);
    }

    @GetMapping("/menu/{id}")
    @Operation(description = "Get Restaurant with menu",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found"
                    )
            })
    public Restaurant getWithMenu(@PathVariable final int id) {
        log.info("get menu for restaurant with id={}", id);
        return repository.getWithMenu(id).orElseThrow(() -> new NotFoundException("Restaurant with id=" + id + " not found"));
    }
}
