package io.github.arthsena.drivestats.app.responses;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.github.arthsena.drivestats.domain.models.User;
import lombok.Data;

import java.util.UUID;

@Data
@JsonRootName("user")
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
