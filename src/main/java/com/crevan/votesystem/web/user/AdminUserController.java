package com.crevan.votesystem.web.user;

import com.crevan.votesystem.error.NotFoundException;
import com.crevan.votesystem.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractUserController {

    static final String REST_URL = "/api/admin/users";

    @GetMapping("/")
    public List<User> getAll() {
        log.info("getting all users");
        return repository.findAll();
    }

    @GetMapping("/by-email")
    public User getByEmail(@RequestParam final String email) {
        log.info("get user with email={}", email);
        return repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new NotFoundException("User with email=" + email.toLowerCase() + " not found"));
    }

    @Override
    @GetMapping("/{id}")
    public User get(@PathVariable final int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final int id) {
        super.delete(id);
    }
}
