package com.crevan.votesystem.web.user;

import com.crevan.votesystem.model.User;
import com.crevan.votesystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Slf4j
public abstract class AbstractUserController {
    @Autowired
    protected UserRepository repository;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public User get(final int id) {
        log.info("get user with id={}", id);
        return repository.getExisted(id);
    }

    public void delete(final int id) {
        log.info("delete user with id={}", id);
        repository.deleteExisted(id);
    }
}
