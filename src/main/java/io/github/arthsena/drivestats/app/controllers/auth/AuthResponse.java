package io.github.arthsena.drivestats.app.controllers.auth;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.github.arthsena.drivestats.app.responses.UserResponse;
import io.github.arthsena.drivestats.domain.models.User;

@JsonRootName("auth")
public class AuthResponse {

    public final String token;
    public final UserResponse user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserResponse(user);
    }

}