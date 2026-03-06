package br.richard.bookingsproject.errors;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> {
    @Schema(description = "Simple message to identify the problem")
    private ExceptionCode error;
    private T details;
}
