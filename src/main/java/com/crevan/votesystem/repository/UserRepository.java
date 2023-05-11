package com.crevan.votesystem.repository;

import com.crevan.votesystem.error.NotFoundException;
import com.crevan.votesystem.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.crevan.votesystem.config.SecurityConfig.PASSWORD_ENCODER;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(final String email);

    @Transactional
    default User prepareAndSave(final User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }

    default User getExistedByEmail(final String email) {
        return findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User with email=" + email.toLowerCase() + " not found"));
    }
}
