package io.github.arthsena.drivestats.app.controllers.profile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

public class ProfileRequest {

    @Getter
    public static class Update {
        private String name;
        private String email;
        @NotBlank(message = "Current password must be not blank")
        private String currentPassword;
        private String newPassword;
        private String confirmPassword;

    }
}
