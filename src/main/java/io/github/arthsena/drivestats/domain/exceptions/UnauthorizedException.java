package io.github.arthsena.drivestats.domain.exceptions;

import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.exception.WebException;

public class UnauthorizedException extends WebException {

    public UnauthorizedException(ExceptionType type) {
        super(type);
    }

}
