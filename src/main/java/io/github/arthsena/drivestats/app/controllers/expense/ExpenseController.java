package io.github.arthsena.drivestats.app.controllers.expense;

import io.github.arthsena.drivestats.domain.services.ExpenseService;
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

@Path("/v1/expense")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpenseController {

    @Inject
    ExpenseService expense;

    @POST
    @RolesAllowed("user")
    public Response create(@Context SecurityContext context, @Valid ExpenseRequest.Create request) {
        return Response.ok(new ExpenseResponse.Single(expense.create((Subject) context.getUserPrincipal(), request))).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("user")
    public Response all(@Context SecurityContext context, @QueryParam("page") Integer page, @QueryParam("limit") Integer limit) {
        return Response.ok(new ExpenseResponse.Multiple(expense.all((Subject) context.getUserPrincipal(), page, limit))).build();
    }

    @POST
    @Path("/{id}")
    @RolesAllowed("user")
    public Response update(@Context SecurityContext context, @PathParam("id") UUID expenseId, @Valid ExpenseRequest.Update request) {
        return Response.ok(new ExpenseResponse.Single(expense.update((Subject) context.getUserPrincipal(), expenseId, request))).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response delete(@Context SecurityContext context, @PathParam("id") UUID expenseId) {
        expense.delete((Subject) context.getUserPrincipal(), expenseId);
        return Response.noContent().build();
    }




}
