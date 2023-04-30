package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.error.IllegalRequestDataException;
import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.util.DateTimeUtil;
import com.crevan.votesystem.util.VoteUtil;
import com.crevan.votesystem.web.AuthUser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/profile/vote";

    private DateTimeUtil dateTimeUtil;

    @Autowired
    public void setDateTimeUtil(final DateTimeUtil dateTimeUtil) {
        this.dateTimeUtil = dateTimeUtil;
    }

    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal final AuthUser authUser) {
        log.info("getting all votes for user with id={}", authUser.id());
        List<Vote> votes =  voteRepository.findAllByUserId(authUser.id());
        System.out.println(votes);
        return VoteUtil.getTos(votes);
    }

    @GetMapping("/{id}")
    public Vote get(@PathVariable final int id) {
        log.info("get vote with id={}", id);
        return voteRepository.getExisted(id);
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Valid @RequestBody final VoteTo voteTo,
                                                   @AuthenticationPrincipal final AuthUser authUser) {
        log.info("create vote={}", voteTo);
        Restaurant restaurant = restaurantRepository.getExisted(voteTo.getRestaurantId());
        Vote newVote = voteRepository.save(new Vote(authUser.getUser(), restaurant));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newVote);
    }

    @PutMapping
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal final AuthUser authUser, @RequestParam final int restaurantId) {
        log.info("update vote for user={}, new restaurant={}", authUser.getUser(), restaurantId);
        if (LocalTime.now().isBefore(dateTimeUtil.getDeadline())) {
            voteRepository.update(authUser.id(), restaurantId);
        } else {
            throw new IllegalRequestDataException("You can change your vote only until 11:00");
        }
    }
}
