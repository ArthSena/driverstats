package io.github.arthsena.drivestats.app.controllers.expense;

import io.github.arthsena.drivestats.domain.services.ExpenseService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/expense")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpenseController {

    @Inject
    ExpenseService expense;

}
