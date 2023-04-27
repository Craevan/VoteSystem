package com.crevan.votesystem.repository;

import com.crevan.votesystem.model.Vote;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    List<Vote> findAllByUserId(final int id);
}
