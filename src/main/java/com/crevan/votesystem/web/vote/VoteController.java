package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.error.IllegalRequestDataException;
import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.util.VoteUtil;
import com.crevan.votesystem.web.AuthUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@Tag(name = "Votes", description = "Vote controller for users")
@ApiResponse(responseCode = "401", description = "Unauthorized")
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/profile/votes";

    @Value("${vote.deadline}")
    private String deadLine;

    @GetMapping
    @Operation(description = "Getting all votes for user",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok")
            })
    public List<VoteTo> getAll(@AuthenticationPrincipal final AuthUser authUser) {
        log.info("getting all votes for user with id={}", authUser.id());
        List<Vote> votes = voteRepository.findAllByUserId(authUser.id());
        System.out.println(votes);
        return VoteUtil.getTos(votes);
    }

    @GetMapping("/{id}")
    @Operation(description = "Getting vote by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            })
    public Vote get(@PathVariable final int id) {
        log.info("get vote with id={}", id);
        return voteRepository.getExisted(id);
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Creates new vote",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            })
    public ResponseEntity<Vote> createWithLocation(@RequestParam final int restaurantId,
                                                   @AuthenticationPrincipal final AuthUser authUser) {
        Vote newVote = voteRepository.save(new Vote(authUser.getUser(), restaurantRepository.get(restaurantId)));
        log.info("create vote={}", newVote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newVote);
    }

    @PutMapping
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Update user's vote",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "409", description = "Conflict"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
            })
    public void update(@AuthenticationPrincipal final AuthUser authUser, @RequestParam final int restaurantId) {
        log.info("update vote for user={}, new restaurantId={}", authUser.getUser(), restaurantId);
        if (LocalTime.now().isBefore(LocalTime.parse(deadLine))) {
            voteRepository.update(authUser.id(), restaurantId);
        } else {
            throw new IllegalRequestDataException("You can change your vote only until " + deadLine);
        }
    }
}
