package br.richard.bookingsproject.errors.exceptions;


import br.richard.bookingsproject.errors.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class InvalidRequestException extends RuntimeException{
    private final ExceptionCode code;

    public InvalidRequestException() {
        super(ExceptionCode.INVALID_REQUEST_EXCEPTION.toString());
        this.code = ExceptionCode.INVALID_REQUEST_EXCEPTION;

        log.error("Invalid request: ", this);
    }

    public InvalidRequestException(ExceptionCode exceptionCode) {
        super(exceptionCode.toString());
        this.code = exceptionCode;

        log.error("Invalid request: ", this);
    }
}
