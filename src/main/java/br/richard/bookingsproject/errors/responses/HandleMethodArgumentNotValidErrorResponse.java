package br.richard.bookingsproject.errors.responses;

import br.richard.bookingsproject.errors.ErrorResponse;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.details.HandleMethodArgumentNotValidErrorDetails;

public class HandleMethodArgumentNotValidErrorResponse extends ErrorResponse<HandleMethodArgumentNotValidErrorDetails> {
    public HandleMethodArgumentNotValidErrorResponse(HandleMethodArgumentNotValidErrorDetails details) {
        super(ExceptionCode.API_FIELDS_INVALID, details);
    }
}
