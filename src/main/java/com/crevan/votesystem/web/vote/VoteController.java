package com.crevan.votesystem.web.vote;

import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;
import com.crevan.votesystem.util.VoteUtil;
import com.crevan.votesystem.web.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
