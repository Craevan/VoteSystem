package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.model.Restaurant;
import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.util.VoteUtil;
import com.crevan.votesystem.web.AuthUser;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController extends AbstractVoteController {

    static final String REST_URL = "/api/profile/vote";

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
        log.info("create voteTo={}", voteTo);
        Restaurant restaurant = restaurantRepository.getExisted(voteTo.getRestaurantId());
        Vote newVote = voteRepository.save(new Vote(authUser.getUser(), restaurant));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(newVote.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(newVote);
    }
}
