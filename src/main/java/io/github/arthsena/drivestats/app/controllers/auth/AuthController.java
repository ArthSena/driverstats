package io.github.arthsena.drivestats.app.controllers.auth;

import io.github.arthsena.drivestats.domain.providers.JwtTokenProvider;
import io.github.arthsena.drivestats.domain.services.AuthService;
import io.github.arthsena.drivestats.infra.security.Subject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

@Path("/v1/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Inject AuthService auth;
    @Inject JwtTokenProvider jwt;

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequest.Login request) {
        var user = auth.login(request);
        var token = jwt.create(user);
        return Response.ok(new AuthResponse(token, user)).build();
    }

    @POST
    @Path("/register")
    public Response register(@Valid AuthRequest.Registration request) {
        var user = auth.register(request);
        var token = jwt.create(user);
        return Response.ok(new AuthResponse(token, user)).build();
    }

    @POST
    @Path("/refresh")
    @RolesAllowed("user")
    public Response refresh(@Context SecurityContext context) {
        var user = auth.refresh((Subject) context.getUserPrincipal());
        var token = jwt.create(user);
        return Response.ok(new AuthResponse(token, user)).build();
    }
}
