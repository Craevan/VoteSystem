package com.crevan.votesystem.repository;

import com.crevan.votesystem.model.Vote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    List<Vote> findAllByUserId(final int id);

    @Modifying
    @Transactional
    @Query("UPDATE Vote v SET v.restaurant.id = ?2 WHERE v.user.id = ?1 AND v.voteDate = current_date")
    void update(final int userId, final int restaurantId);

    @Query("SELECT v, u FROM Vote v JOIN User u ON v.user.id = u.id  WHERE v.restaurant.id = ?1")
    List<Vote> getByRestaurantId(final int id);

    @Query("SELECT v FROM Vote v WHERE v.voteDate = ?1")
    List<Vote> getByDate(final LocalDate date);
}
