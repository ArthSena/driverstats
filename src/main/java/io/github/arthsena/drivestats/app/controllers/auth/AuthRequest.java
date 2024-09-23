package io.github.arthsena.drivestats.app.controllers.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

public class AuthRequest {

    @Getter
    public static class Login {
        @NotBlank(message = "Email must be not blank")
        private String email;

        @NotBlank(message = "Password must be not blank")
        private String password;
    }

    @Getter
    public static class Registration {
        @NotBlank(message = "Name must be not blank")
        private String name;

        @NotBlank(message = "Email must be not blank")
        @Email(message = "Email must be an valid email address")
        private String email;

        @NotBlank(message = "password must be not blank")
        @Length(min = 4, message = "password must be at least 4 characters")
        private String password;

        @NotBlank(message = "Confirm password must be not blank")
        @Length(min = 4, message = "Confirm password must be at least 4 characters")
        private String confirmPassword;
    }
}