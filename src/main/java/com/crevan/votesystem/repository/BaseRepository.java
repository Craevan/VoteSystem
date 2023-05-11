package com.crevan.votesystem.repository;

import com.crevan.votesystem.error.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM #{#entityName} e WHERE e.id=:id")
    int delete(final int id);

    @Query("SELECT e FROM #{#entityName} e WHERE e.id = :id")
    T get(final int id);

    default void deleteExisted(final int id) {
        if (delete(id) == 0) {
            throw new NotFoundException("Entity with id=" + id + " not found");
        }
    }

    default T getExisted(final int id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}
