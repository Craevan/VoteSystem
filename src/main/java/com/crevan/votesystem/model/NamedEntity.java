package com.crevan.votesystem.model;

import com.crevan.votesystem.util.validation.NoHtml;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@MappedSuperclass
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NamedEntity extends BaseEntity {

    @NoHtml
    @NotBlank
    @Size(max = 128)
    @Column(name = "name", nullable = false)
    protected String name;

    protected NamedEntity(final Integer id, final String name) {
        super(id);
        this.name = name;
    }
}
