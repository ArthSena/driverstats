package io.github.arthsena.drivestats.app.controllers.registry;

import io.github.arthsena.drivestats.domain.services.CashRegistryService;
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

@Path("/v1/registry")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistryController {

    @Inject
    CashRegistryService registry;

    @POST
    @RolesAllowed("user")
    public Response create(@Context SecurityContext context, @Valid RegistryRequest.Create request) {
        return Response.ok(new RegistryResponse.Single(registry.create((Subject) context.getUserPrincipal(), request))).build();
    }

    @GET
    @Path("/date")
    @RolesAllowed("user")
    public Response searchByDate(@Context SecurityContext context, @QueryParam("minPeriod") String minPeriod, @QueryParam("maxPeriod") String maxPeriod) {
        return Response.ok(new RegistryResponse.Multiple(registry.searchByDate((Subject) context.getUserPrincipal(), minPeriod, maxPeriod))).build();
    }

    @GET
    @Path("/all")
    @RolesAllowed("user")
    public Response all(@Context SecurityContext context, @QueryParam("page") Integer page, @QueryParam("limit") Integer limit) {
        return Response.ok(new RegistryResponse.Multiple(registry.all((Subject) context.getUserPrincipal(), page, limit))).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed("user")
    public Response all(@Context SecurityContext context) {
        return Response.ok(registry.count((Subject) context.getUserPrincipal())).build();
    }

    @POST
    @Path("/{id}/close")
    @RolesAllowed("user")
    public Response close(@Context SecurityContext context, @Valid RegistryRequest.Close request, @PathParam("id") UUID registryId) {
        return Response.ok(new RegistryResponse.Single(registry.close((Subject) context.getUserPrincipal(), registryId, request))).build();
    }

    @POST
    @Path("/{id}/reopen")
    @RolesAllowed("user")
    public Response close(@Context SecurityContext context, @PathParam("id") UUID registryId) {
        return Response.ok(new RegistryResponse.Single(registry.reopen((Subject) context.getUserPrincipal(), registryId))).build();
    }

    @POST
    @Path("/{id}")
    @RolesAllowed("user")
    public Response update(@Context SecurityContext context, @Valid RegistryRequest.Update request, @PathParam("id") UUID registryId) {
        return Response.ok(new RegistryResponse.Single(registry.update((Subject) context.getUserPrincipal(), registryId, request))).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    public Response getById(@Context SecurityContext context, @PathParam("id") UUID registryId) {
        return Response.ok(new RegistryResponse.Single(registry.getById((Subject) context.getUserPrincipal(), registryId))).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("user")
    public Response delete(@Context SecurityContext context, @PathParam("id") UUID registryId) {
        registry.delete((Subject) context.getUserPrincipal(), registryId);
        return Response.noContent().build();
    }
}
