package br.richard.bookingsproject.errors.exceptions;

import br.richard.bookingsproject.errors.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Slf4j
public class DuplicatedResourceException extends RuntimeException {
    private final ExceptionCode code;
    private final Class<?> resourceClass;
    private final Map<String, Object> parameters;

    public DuplicatedResourceException(Class<?> resourceClass) {
        super(ExceptionCode.DUPLICATED_RESOURCE.toString());
        this.code = ExceptionCode.DUPLICATED_RESOURCE;
        this.resourceClass = resourceClass;
        this.parameters = null;

        log.error(this.getMessage(), this);
    }

    public DuplicatedResourceException(Class<?> resourceClass, Map<String, Object> parameters) {
        super(ExceptionCode.DUPLICATED_RESOURCE.toString());
        this.code = ExceptionCode.DUPLICATED_RESOURCE;
        this.resourceClass = resourceClass;
        this.parameters = parameters;

        log.error(this.getMessage(), this);
    }
}
