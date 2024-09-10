package io.github.arthsena.drivestats.infra.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {

    PARAM_NOT_FOUND(2000, "a requested param", "%s was not found"),
    PARAM_ALREADY_EXISTS(2001, "a requested param", "%s already exists"),
    ENTITY_NOT_FOUND(2003, "entity", "%s was not found"),

    PASSWORDS_NOT_MATCH(2100, "", "passwords do not match"),

    INVALID_SUBJECT(2101, "Current subject", "%s is not a valid subject"),
    INVALID_PASSWORD(2101, "", "invalid password"),

    UNAUTHORIZED(3000, "Current subject", "%s is not authorized to access this"),

    JWT_VERIFICATION_EXCEPTION(5000, "", "invalid token");

    private int code;
    private String pattern;
    private String body;

    ExceptionType(int code, String pattern, String body) {
        this.code = code;
        this.pattern = pattern;
        this.body = body;
    }

    public ExceptionType withCode(int code) {
        this.code = code;
        return this;
    }

    public ExceptionType withPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public ExceptionType withBody(String body) {
        this.body = body;
        return this;
    }

    public ExceptionType withMessage(String message) {
        this.pattern = "";
        this.body = message;
        return this;
    }

    public String getMessage() {
        try {
            return body.formatted(pattern);
        }catch (Exception e) {
            return body;
        }
    }
}