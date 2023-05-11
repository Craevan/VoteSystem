package com.crevan.votesystem.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class NamedEntityTo extends BaseEntityTo {

    @NotBlank
    @Size(max = 128)
    protected String name;

    public NamedEntityTo(final Integer id, final String name) {
        super(id);
        this.name = name;
    }
}
