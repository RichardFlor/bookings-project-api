package br.richard.bookingsproject.errors.exceptions;

import br.richard.bookingsproject.errors.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ImageManipulationException extends RuntimeException {
    private final ExceptionCode code;
    private Map<String, Object> details;

    public ImageManipulationException(ExceptionCode code, Map<String, Object> details) {
        super(code.toString());
        this.code = code;
        this.details = details;
    }

    public ImageManipulationException(ExceptionCode code) {
        super(code.toString());
        this.code = code;
        this.details = null;
    }
}
