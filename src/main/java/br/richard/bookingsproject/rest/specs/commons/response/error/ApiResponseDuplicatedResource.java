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
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@ApiResponse(
        responseCode = "409",
        description = "${springdoc.swagger-config.responses.error.409}",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(
                        implementation = ErrorResponse.class,
                        example = "{ \"error\": \"DUPLICATED_RESOURCE\", \"details\": {} }"
                )
        )
)
public @interface ApiResponseDuplicatedResource {
}