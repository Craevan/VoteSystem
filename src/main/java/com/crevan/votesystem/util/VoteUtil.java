package com.crevan.votesystem.util;

import com.crevan.votesystem.model.Vote;
import com.crevan.votesystem.to.VoteTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class VoteUtil {

    public static VoteTo asTo(final Vote vote) {
        return new VoteTo(vote.id(), vote.getRestaurant().getId(), vote.getVoteDate());
    }

    public static List<VoteTo> getTos(final Collection<Vote> votes) {
        return votes.stream()
                .map(VoteUtil::asTo)
                .collect(Collectors.toList());
    }
}
