package com.crevan.votesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@ToString(callSuper = true, exclude = "password")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends NamedEntity implements Serializable {

    @Email
    @NotEmpty
    @Size(max = 128)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @JsonIgnore
    @Size(max = 256)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false, columnDefinition = "bool default true")
    private boolean isActive = true;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    private Date registered = new Date();

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "users_roles_unique"))
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }
}
