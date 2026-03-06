package br.richard.bookingsproject.errors.responses;

import br.richard.bookingsproject.errors.ErrorResponse;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.details.InvalidFieldErrorDetails;

public class InvalidFieldErrorResponse extends ErrorResponse<InvalidFieldErrorDetails> {
    public InvalidFieldErrorResponse(InvalidFieldErrorDetails details) {
        super(ExceptionCode.API_FIELDS_INVALID, details);
    }
}
