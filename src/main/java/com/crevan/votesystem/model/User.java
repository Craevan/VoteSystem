package com.crevan.votesystem.model;

import com.crevan.votesystem.HasIdAndEmail;
import com.crevan.votesystem.util.validation.NoHtml;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "users")
@ToString(callSuper = true, exclude = "password")
public class User extends NamedEntity implements Serializable, HasIdAndEmail {

    @Serial
    private static final long serialVersionUID = 1L;

    @Email
    @NoHtml
    @NotBlank
    @Size(max = 128)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank
    @Size(max = 128)
    @Column(name = "password", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    public User(final User user) {
        this(user.id(), user.name, user.email, user.password, user.isActive, user.registered, user.roles);
    }

    public User(final Integer id, final String name, final String password, final String email, final Role... role) {
        super(id, name);
        this.password = password;
        this.email = email;
        this.roles = Set.of(role);
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.isActive = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    public boolean hasRole(Role role) {
        return roles != null && roles.contains(role);
    }
}
