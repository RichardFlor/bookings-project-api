package br.richard.bookingsproject.errors.responses;

import br.richard.bookingsproject.errors.ErrorResponse;
import br.richard.bookingsproject.errors.ExceptionCode;
import br.richard.bookingsproject.errors.details.EntityNotFoundErrorDetails;

public class EntityNotFoundErrorResponse extends ErrorResponse<EntityNotFoundErrorDetails> {
    public EntityNotFoundErrorResponse(EntityNotFoundErrorDetails details) {
        super(ExceptionCode.ENTITY_NOT_FOUND, details);
    }
}
