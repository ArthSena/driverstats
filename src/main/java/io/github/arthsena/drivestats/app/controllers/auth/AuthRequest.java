package io.github.arthsena.drivestats.app.controllers.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class AuthRequest {

    @Getter
    public static class Login {
        @NotBlank(message = "parameter 'email' must be not blank")
        private String email;

        @NotBlank(message = "parameter 'password' must be not blank")
        private String password;
    }

    @Getter
    public static class Registration {
        @NotBlank(message = "parameter 'name' must be not blank")
        private String name;

        @NotBlank(message = "parameter 'email' must be not blank")
        @Email(message = "parameter 'email' must be an valid email address")
        private String email;

        @NotBlank(message = "parameter 'password' must be not blank")
        @Length(min = 4, message = "parameter 'password' must be at least 4 characters")
        private String password;

        @NotBlank(message = "parameter 'repassword' must be not blank")
        @Length(min = 4, message = "parameter 'repassword' must be at least 4 characters")
        private String repassword;
    }
}