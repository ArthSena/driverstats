package io.github.arthsena.drivestats.domain.exceptions;

import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.exception.WebException;

public class ConflictException extends WebException {

    public ConflictException(ExceptionType type) {
        super(type);
    }

}
