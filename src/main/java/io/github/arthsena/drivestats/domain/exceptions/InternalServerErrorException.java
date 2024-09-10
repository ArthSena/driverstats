package io.github.arthsena.drivestats.domain.exceptions;

import io.github.arthsena.drivestats.infra.exception.ExceptionType;
import io.github.arthsena.drivestats.infra.exception.WebException;

public class InternalServerErrorException extends WebException {

    public InternalServerErrorException(ExceptionType type) {
        super(type);
    }

}
