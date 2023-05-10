package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.error.SwaggerExceptionInfo;
import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.util.VoteUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Votes-admin", description = "Vote controller for admins")
@ApiResponse(responseCode = "401", description = "Unauthorized",
        content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController extends AbstractVoteController {

    static final String REST_URL = "/api/admin/votes";

    @GetMapping
    @Operation(description = "Getting all votes for all users",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<VoteTo> getAll() {
        log.info("Get all votes");
        return VoteUtil.getTos(voteRepository.findAll());
    }

    @GetMapping(value = "/by-user/{id}")
    @Operation(description = "Get all votes by userId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<VoteTo> getByUserId(@PathVariable final int id) {
        log.info("Get all votes for user with id={}", id);
        return VoteUtil.getTos(voteRepository.findAllByUserId(id));
    }

    @GetMapping(value = "/by-restaurant/{id}")
    @Operation(description = "Get all votes by restaurantId",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<VoteTo> getByRestaurant(@PathVariable final int id) {
        log.info("Get all votes for restaurantId={}", id);
        return VoteUtil.getTos(voteRepository.getByRestaurantId(id));
    }

    @GetMapping(value = "/by-date/{date}")
    @Operation(description = "Get all votes for date",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<VoteTo> getByDate(@PathVariable final LocalDate date) {
        log.info("Get all votes for date={}", date);
        return VoteUtil.getTos(voteRepository.getByDate(date));
    }
}
