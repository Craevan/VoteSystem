package com.crevan.votesystem.web.user;

import com.crevan.votesystem.error.SwaggerExceptionInfo;
import com.crevan.votesystem.model.User;
import com.crevan.votesystem.util.validation.ValidationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@CacheConfig(cacheNames = "users")
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Controller for Administrators")
@ApiResponses(value = {
        @ApiResponse(responseCode = "401", description = "Unauthorized",
                content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden",
                content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
})
public class AdminUserController extends AbstractUserController {

    static final String REST_URL = "/api/admin/users";

    @CacheEvict(value = "users", allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Create new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Created"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
            })
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        log.info("create {}", user);
        ValidationUtil.checkNew(user);
        ValidationUtil.checkRoles(user);
        User created = repository.prepareAndSave(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", allEntries = true)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Update existed user",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
            })
    public void update(@Valid @RequestBody final User user, @PathVariable final int id) {
        log.info("updating {} with id={}", user, id);
        ValidationUtil.assureIdConsistent(user, id);
        repository.prepareAndSave(user);
    }

    @Cacheable
    @GetMapping
    @Operation(description = "Get all users", responses = {@ApiResponse(responseCode = "200", description = "Ok")})
    public List<User> getAll() {
        log.info("getting all users");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @GetMapping("/by-email")
    @Operation(description = "Get user by e-mail",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
            })
    public User getByEmail(@RequestParam final String email) {
        log.info("get user with email={}", email);
        return repository.getExistedByEmail(email);
    }

    @Override
    @GetMapping("/{id}")
    @Operation(description = "Get user by Id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "404", description = "Not Found",
                            content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
            })
    public User get(@PathVariable final int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", allEntries = true)
    @Operation(description = "Delete user by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
            })
    public void delete(@PathVariable final int id) {
        super.delete(id);
    }

    @Transactional
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", allEntries = true)
    @Operation(description = "Enable/Disable user account",
            responses = {
                    @ApiResponse(responseCode = "204", description = "No Content"),
                    @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                            content = @Content(schema = @Schema(implementation = SwaggerExceptionInfo.class)))
            })
    public void enable(@PathVariable final int id, @RequestParam final boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        User user = repository.getExisted(id);
        user.setActive(enabled);
    }
}
