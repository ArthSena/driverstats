package io.github.arthsena.drivestats.infra.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WebException extends RuntimeException {

    private final ExceptionType type;
    private final int code;

    public WebException(ExceptionType type) {
        super(type.getMessage());
        this.type = type;
        this.code = type.getCode();
    }

}
