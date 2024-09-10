package io.github.arthsena.drivestats.domain.exceptions;

import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.exception.WebException;

public class BadRequestException extends WebException {

    public BadRequestException(ExceptionType type) {
        super(type);
    }

}
