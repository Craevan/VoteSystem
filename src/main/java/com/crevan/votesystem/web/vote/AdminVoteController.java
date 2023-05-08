package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.model.Vote;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Votes-admin", description = "Vote controller for admins")
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController extends AbstractVoteController {

    static final String REST_URL = "/api/admin/votes";

    @GetMapping
    public List<Vote> getAll() {
        log.info("Get all votes");
        return voteRepository.findAll();
    }

    @GetMapping(value = "/by-user/{id}")
    public List<Vote> getByUserId(@PathVariable final int id) {
        log.info("Get all votes for user with id={}", id);
        return voteRepository.findAllByUserId(id);
    }

    @GetMapping(value = "/by-restaurant/{id}")
    public List<Vote> getByRestaurant(@PathVariable final int id) {
        log.info("Get all votes for restaurantId={}", id);
        return voteRepository.getByRestaurantId(id);
    }

    @GetMapping(value = "/by-date/{date}")
    public List<Vote> getByDate(@PathVariable final LocalDate date) {
        log.info("Get all votes for date={}", date);
        return voteRepository.getByDate(date);
    }
}
