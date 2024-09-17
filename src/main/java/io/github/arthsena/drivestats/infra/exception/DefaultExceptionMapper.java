package io.github.arthsena.drivestats.infra.exception;

import io.github.arthsena.drivestats.app.responses.ExceptionResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception e) {
		ExceptionResponse res = new ExceptionResponse();

		res.setCode(0);
		res.setMessage(e.getMessage());

		e.printStackTrace();

		return Response.ok(res).status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
}