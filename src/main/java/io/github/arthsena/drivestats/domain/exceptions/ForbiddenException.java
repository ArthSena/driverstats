package io.github.arthsena.drivestats.domain.exceptions;

import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.exception.WebException;

public class ForbiddenException extends WebException {

    public ForbiddenException(ExceptionType type) {
        super(type);
    }

}
