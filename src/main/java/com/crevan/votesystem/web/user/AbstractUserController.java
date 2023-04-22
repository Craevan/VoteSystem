package com.crevan.votesystem.web.user;

import com.crevan.votesystem.model.User;
import com.crevan.votesystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractUserController {
    @Autowired
    protected UserRepository repository;

    public User get(int id) {
        log.info("get user with id={}", id);
        return repository.getExisted(id);
    }

    public void delete(int id) {
        log.info("delete user with id={}", id);
        repository.deleteExisted(id);
    }
}
