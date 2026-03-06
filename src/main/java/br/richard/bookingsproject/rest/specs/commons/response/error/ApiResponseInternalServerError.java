package br.richard.bookingsproject.rest.specs.commons.response.error;

import br.richard.bookingsproject.errors.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@ApiResponse(
        responseCode = "500",
        description = "${springdoc.swagger-config.responses.error.500}",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(
                        implementation = ErrorResponse.class,
                        example = "{ \"error\": \"INTERNAL_SERVER_ERROR\", \"details\": {} }"
                )
        )
)
public @interface ApiResponseInternalServerError {
}
