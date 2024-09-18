package io.github.arthsena.drivestats.app.controllers.profile;

import io.github.arthsena.drivestats.app.controllers.expense.ExpenseRequest;
import io.github.arthsena.drivestats.app.controllers.expense.ExpenseResponse;
import io.github.arthsena.drivestats.app.responses.UserResponse;
import io.github.arthsena.drivestats.domain.services.ProfileService;
import io.github.arthsena.drivestats.infra.security.Subject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.UUID;

@Path("/v1/profile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileController {

    @Inject
    ProfileService profile;

    @POST
    @RolesAllowed("user")
    public Response update(@Context SecurityContext context, @Valid ProfileRequest.Update request) {
        return Response.ok(new UserResponse(profile.update((Subject) context.getUserPrincipal(), request))).build();
    }
}
