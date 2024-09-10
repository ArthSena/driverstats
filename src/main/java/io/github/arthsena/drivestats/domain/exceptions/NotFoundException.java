package io.github.arthsena.drivestats.domain.exceptions;

import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.exception.WebException;

public class NotFoundException extends WebException {

    public NotFoundException(ExceptionType type) {
        super(type);
    }

}
