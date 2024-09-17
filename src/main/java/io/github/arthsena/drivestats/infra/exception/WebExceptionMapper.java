package io.github.arthsena.drivestats.infra.exception;

import io.github.arthsena.drivestats.app.responses.ExceptionResponse;
import io.github.arthsena.drivestats.domain.exceptions.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Provider
public class WebExceptionMapper implements ExceptionMapper<WebException> {

    private final Map<Class<? extends WebException>, Function<WebException, Response>> exceptionMapper;

    public WebExceptionMapper() {
        final var handlerMap = new HashMap<Class<? extends WebException>, Function<WebException, Response>>();

        handlerMap.put(NotFoundException.class, this::notFound);
        handlerMap.put(ConflictException.class, this::conflict);
        handlerMap.put(UnauthorizedException.class, this::unauthorized);
        handlerMap.put(ForbiddenException.class, this::forbidden);
        handlerMap.put(InternalServerErrorException.class, this::internalError);
        handlerMap.put(BadRequestException.class, this::badRequest);

        this.exceptionMapper = handlerMap;
    }
    private ExceptionResponse response(WebException exception) {
        return new ExceptionResponse(exception.getCode(), exception.getMessage());
    }

    private Response notFound(WebException exception) {
        return Response.ok(response(exception))
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .build();
    }

    private Response conflict(WebException exception) {
        return Response.ok(response(exception))
                .status(Response.Status.CONFLICT.getStatusCode())
                .build();
    }

    private Response unauthorized(WebException exception) {
        return Response.ok(response(exception))
                .status(Response.Status.UNAUTHORIZED.getStatusCode())
                .build();
    }

    private Response forbidden(WebException exception) {
        return Response.ok(response(exception))
                .status(Response.Status.FORBIDDEN)
                .build();
    }

    private Response internalError(WebException exception) {
        return Response.ok(response(exception))
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .build();
    }

    private Response badRequest(WebException exception) {
        return Response.ok(response(exception))
                .status(Response.Status.BAD_REQUEST)
                .build();
    }

    @Override
    public Response toResponse(WebException exception) {
        return this.exceptionMapper.get(exception.getClass()).apply(exception);
    }
}
