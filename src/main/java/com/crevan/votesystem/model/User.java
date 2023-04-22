package com.crevan.votesystem.model;

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
    @Size(max = 256)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "registered", nullable = false, updatable = false)
    @NotNull
    private Date registered = new Date();

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "users_roles_unique"))
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
}
