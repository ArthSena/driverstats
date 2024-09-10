package io.github.arthsena.drivestats.app.controllers.auth;

import io.github.arthsena.drivestats.app.responses.UserResponse;
import io.github.arthsena.drivestats.domain.models.User;

public class AuthResponse {

    public String token;
    public UserResponse user;

    public AuthResponse(String token, User user) {
        this.token = token;
        this.user = new UserResponse(user);
    }

}