package io.github.arthsena.drivestats.app.controllers.profile;

import lombok.Getter;

public class ProfileRequest {

    @Getter
    public static class Update {
        private String name;
        private String email;
        private String currentPassword;
        private String newPassword;
        private String confirmPassword;
    }
}
