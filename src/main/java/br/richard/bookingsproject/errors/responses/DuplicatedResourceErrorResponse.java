package br.richard.bookingsproject.errors.responses;

import br.richard.bookingsproject.errors.ErrorResponse;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.details.DuplicatedResourceErrorDetails;

public class DuplicatedResourceErrorResponse extends ErrorResponse<DuplicatedResourceErrorDetails> {
    public DuplicatedResourceErrorResponse(DuplicatedResourceErrorDetails details) {
        super(ExceptionCode.DUPLICATED_RESOURCE, details);
    }
}
