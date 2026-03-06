package br.richard.bookingsproject.errors.exceptions;

import br.richard.bookingsproject.errors.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class UnauthorizedException extends RuntimeException {
    private final ExceptionCode code;
    private final Exception originalException;

    public UnauthorizedException() {
        super(ExceptionCode.UNAUTHORIZED.toString());
        this.code = ExceptionCode.UNAUTHORIZED;
        this.originalException = null;
        log.error("Unauthorized request!", this);
    }

    public UnauthorizedException(Exception ex) {
        super(ExceptionCode.UNAUTHORIZED.toString());
        this.code = ExceptionCode.UNAUTHORIZED;
        this.originalException = ex;
        log.error("Unauthorized request!", this);
    }
}
