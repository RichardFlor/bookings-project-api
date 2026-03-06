package br.richard.bookingsproject.errors.exceptions;

import br.richard.bookingsproject.errors.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class AuthenticationTokenCreationException extends RuntimeException {
    private final ExceptionCode code;
    private final Exception originalException;

    public AuthenticationTokenCreationException(Exception ex) {
        super(ExceptionCode.AUTHENTICATION_TOKEN_CREATION_ERROR.toString());
        this.code = ExceptionCode.AUTHENTICATION_TOKEN_CREATION_ERROR;
        this.originalException = ex;

        log.error("Authentication token creation error: ", this);
    }
}
