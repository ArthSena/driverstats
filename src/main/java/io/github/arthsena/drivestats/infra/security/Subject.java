package io.github.arthsena.drivestats.infra.security;

import io.github.arthsena.drivestats.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.security.Principal;
import java.util.UUID;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Subject implements Principal {

    private UUID id;
    private String name;
    private String email;
    private User.Role role;

}
