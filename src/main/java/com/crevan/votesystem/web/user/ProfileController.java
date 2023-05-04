package com.crevan.votesystem.web.user;

import com.crevan.votesystem.model.User;
import com.crevan.votesystem.to.UserTo;
import com.crevan.votesystem.util.UserUtil;
import com.crevan.votesystem.util.validation.ValidationUtil;
import com.crevan.votesystem.web.AuthUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@AllArgsConstructor
@CacheConfig(cacheNames = "users")
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController extends AbstractUserController {

    static final String REST_URL = "/api/profile";

    @GetMapping
    public User get(@AuthenticationPrincipal final AuthUser authUser) {
        log.info("get {}", authUser);
        return authUser.getUser();
    }

    @DeleteMapping
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal final AuthUser authUser) {
        log.info("delete {}", authUser);
        super.delete(authUser.id());
    }

    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> register(@Valid @RequestBody final UserTo userTo) {
        log.info("register {}", userTo);
        ValidationUtil.checkNew(userTo);
        User newUser = repository.save(UserUtil.prepareToSave(UserUtil.createNewFromTo(userTo)));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL)
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(newUser);
    }

    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody @Valid final UserTo userTo, @AuthenticationPrincipal final AuthUser authUser) {
        log.info("update {} to {}", authUser, userTo);
        User user = authUser.getUser();
        repository.prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }
}
