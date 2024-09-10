package io.github.arthsena.drivestats.domain.models;

import io.github.arthsena.drivestats.infra.database.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {

    private UUID id;

    private String name;
    private String email;
    private String password;

    private Role role;

    public User(String name, String email, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;
    }

    public User(UserEntity entity) {
        this(entity.getId(), entity.getName(), entity.getEmail(), entity.getPassword(), entity.getRole());
    }

    public enum Role {
        USER,
        ADMIN
    }
}
