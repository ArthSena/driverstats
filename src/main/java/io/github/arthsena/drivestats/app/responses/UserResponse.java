package io.github.arthsena.drivestats.app.responses;

import io.github.arthsena.drivestats.domain.models.User;
import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private String name;
    private String email;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
