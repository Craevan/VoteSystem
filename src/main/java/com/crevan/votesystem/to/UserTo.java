package com.crevan.votesystem.to;

import com.crevan.votesystem.HasIdAndEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class UserTo extends NamedEntityTo implements HasIdAndEmail {

    @Email
    @NotEmpty(message = "email can't be empty")
    @Size(max = 128)
    private String email;

    @NotBlank
    @Size(max = 256)
    private String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id, name);
        this.email = email;
        this.password = password;
    }
}
