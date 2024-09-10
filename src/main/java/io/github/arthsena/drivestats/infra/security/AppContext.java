package io.github.arthsena.drivestats.infra.security;

import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.SecurityContext;
import lombok.Getter;

import java.security.Principal;

public class AppContext implements SecurityContext {

    @Getter private final Subject subject;

    public AppContext(Subject subject) {
        this.subject = subject;
    }

    @Override
    public Principal getUserPrincipal() {
        return subject;
    }

    @Override
    public boolean isUserInRole(String role) {
        return role.equalsIgnoreCase(subject.getRole().toString());
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public String getAuthenticationScheme() {
        return "";
    }
}
