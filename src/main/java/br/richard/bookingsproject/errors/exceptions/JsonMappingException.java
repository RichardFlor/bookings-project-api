package br.richard.bookingsproject.errors.exceptions;

import br.richard.bookingsproject.errors.ExceptionCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class JsonMappingException extends RuntimeException{

    private final ExceptionCode code;
    private final Exception exception;
    public JsonMappingException(Exception exception) {
        super(ExceptionCode.JSON_MAPPING_ERROR.toString());
        this.code = ExceptionCode.JSON_MAPPING_ERROR;
        this.exception = exception;
        log.error("An error occurred while mapping a json");
    }
    public String getCodeAsString() {
        return code.toString();
    }
}
