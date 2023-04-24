package com.crevan.votesystem.to;

import com.crevan.votesystem.HasIdAndEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString(callSuper = true)
public class UserTo extends NamedEntityTo implements HasIdAndEmail {

    @Email
    @NotEmpty(message = "email can't be empty")
    @Size(max = 128)
    private String email;

    @Size(max = 256)
    private String password;
}
