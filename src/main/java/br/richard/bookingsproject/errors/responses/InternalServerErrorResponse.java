package br.richard.bookingsproject.errors.responses;

import br.richard.bookingsproject.errors.ErrorResponse;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.details.InternalServerErrorDetails;

public class InternalServerErrorResponse extends ErrorResponse<InternalServerErrorDetails> {
    public InternalServerErrorResponse(InternalServerErrorDetails details) {
        super(ExceptionCode.INTERNAL_SERVER_ERROR, details);
    }
}
