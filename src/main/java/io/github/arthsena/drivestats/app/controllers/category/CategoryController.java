package io.github.arthsena.drivestats.app.controllers.category;

import io.github.arthsena.drivestats.domain.services.CategoryService;
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

@Path("/v1/category")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryService category;

    @POST
    @RolesAllowed("user")
    public Response create(@Context SecurityContext context, @Valid CategoryRequest.Create request) {
        return Response.ok(new CategoryResponse.Single(category.create((Subject) context.getUserPrincipal(), request))).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("user")
    public Response all(@Context SecurityContext context) {
        return Response.ok(new CategoryResponse.Multiple(category.all((Subject) context.getUserPrincipal()))).build();
    }

    @POST
    @Path("/{id}")
    @RolesAllowed("user")
    public Response update(@Context SecurityContext context, @PathParam("id") UUID categoryId, @Valid CategoryRequest.Update request) {
        return Response.ok(new CategoryResponse.Single(category.update((Subject) context.getUserPrincipal(), categoryId, request))).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response delete(@Context SecurityContext context, @PathParam("id") UUID categoryId) {
        category.delete((Subject) context.getUserPrincipal(), categoryId);
        return Response.noContent().build();
    }


}
