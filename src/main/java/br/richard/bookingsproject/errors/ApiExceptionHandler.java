package br.richard.bookingsproject.errors;

import br.richard.bookingsproject.errors.details.*;
import br.richard.bookingsproject.errors.exceptions.*;
import br.richard.bookingsproject.errors.responses.*;
import br.richard.bookingsproject.errors.i18n.MessageService;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageService messageService;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        final var field = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField();
        final var messages = ex.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toArray(String[]::new);

        final var body = new HandleMethodArgumentNotValidErrorResponse(new HandleMethodArgumentNotValidErrorDetails(field, messages));

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    private HashMap<String, Object> getExceptionCodeMessage(ExceptionCode exceptionCode) {
        var message = messageService.get(exceptionCode);
        var details = new HashMap<String, Object>();
        details.put("message", message);

        return details;
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> badCredentialsExceptionHandler(
            Exception ex, WebRequest request
    ) {
        final var body = new ErrorResponse<>(ExceptionCode.BAD_CREDENTIALS, messageService.get(ExceptionCode.BAD_CREDENTIALS));

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> internalServerErrorExceptionHandler(
            Exception ex, WebRequest request) {

        final var body = new InternalServerErrorResponse(new InternalServerErrorDetails(ex.getMessage()));

        log.error(ex.getMessage(), ex);

        return handleExceptionInternal(
                ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> businessExceptionHandler(
            BusinessRuleException exception,
            WebRequest request
    ) {
        exception.setDetails(this.getExceptionCodeMessage(exception.getCode()));

        log.error(exception.getMessage(), exception);
        final var responseBody = new ErrorResponse<>(
                exception.getCode(),
                exception.getDetails()
        );

        return handleExceptionInternal(
                exception,
                responseBody,
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                request
        );
    }

    @ExceptionHandler(ImageManipulationException.class)
    public ResponseEntity<Object> handleImageManipulationException(
            ImageManipulationException exception,
            WebRequest request
    ) {
        exception.setDetails(this.getExceptionCodeMessage(exception.getCode()));

        log.error(exception.getMessage(), exception);
        final var responseBody = new ErrorResponse<>(
                exception.getCode(),
                exception.getDetails()
        );

        return this.handleExceptionInternal(
                exception,
                responseBody,
                new HttpHeaders(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                request
        );
    }

    @ExceptionHandler(value = {InvalidFieldException.class})
    public ResponseEntity<Object> invalidFieldExceptionHandler(InvalidFieldException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);

        final var body = new InvalidFieldErrorResponse(new InvalidFieldErrorDetails(ex.getFields()));

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex, WebRequest request) {
        final var cause = Objects.nonNull(ex.getCause().getCause()) ? ex.getCause().getCause() : ex.getCause();
        final var body = new ErrorResponse<>(
                ExceptionCode.API_FIELDS_INVALID,
                Map.of("cause", Objects.nonNull(cause.getMessage()) ? cause.getMessage() : ex.getMessage())
        );

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public ResponseEntity<Object> duplicateKeyExceptionHandler(DuplicateKeyException ex, WebRequest request) {
        final var cause = Objects.nonNull(ex.getCause().getCause()) ? ex.getCause().getCause() : ex.getCause();
        final var body = new ErrorResponse<>(
                ExceptionCode.API_FIELDS_INVALID,
                Map.of("cause", Objects.nonNull(cause.getMessage()) ? cause.getMessage() : ex.getMessage())
        );

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> entityNotFoundExceptionHandler(EntityNotFoundException ex, WebRequest request) {
        final var body = new EntityNotFoundErrorResponse(
                new EntityNotFoundErrorDetails(
                        ex.getEntityClass().getSimpleName(),
                        ex.getParameters()));

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {InvalidRequestException.class})
    public ResponseEntity<Object> invalidRequestExceptionHandler(InvalidRequestException ex, WebRequest request) {
        final var body = new ErrorResponse<>(ExceptionCode.INVALID_REQUEST_EXCEPTION, getExceptionCodeMessage(ex.getCode()));

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler(value = {DuplicatedResourceException.class})
    public ResponseEntity<Object> duplicateResourceExceptionHandler(DuplicatedResourceException ex, WebRequest request) {
        final var body = new DuplicatedResourceErrorResponse(
                new DuplicatedResourceErrorDetails(ex.getResourceClass(), ex.getParameters())
        );

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {EmailNotSentException.class})
    public ResponseEntity<Object> emailNotSentExceptionHandler(EmailNotSentException ex, WebRequest request) {
        final var body = new ErrorResponse<>(
                ExceptionCode.EMAIL_NOT_SENT,
                Map.of("sentTo", ex.getTo())
        );

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> unauthorizedExceptionHandler(UnauthorizedException ex, WebRequest request) {
        final var body = new ErrorResponse<>(ExceptionCode.UNAUTHORIZED, getExceptionCodeMessage(ex.getCode()));
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<Object> forbiddenExceptionHandler(ForbiddenException ex, WebRequest request) {
        final var body = new ErrorResponse<>(ExceptionCode.FORBIDDEN, getExceptionCodeMessage(ex.getCode()));

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = {ExpiredTokenException.class})
    public ResponseEntity<Object> expiredTokenExceptionHandler(ExpiredTokenException ex, WebRequest request) {
        final var body = new ErrorResponse<>(ExceptionCode.EXPIRED_TOKEN, getExceptionCodeMessage(ex.getCode()));

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UsedTokenException.class})
    public ResponseEntity<Object> usedTokenExceptionHandler(UsedTokenException ex, WebRequest request) {
        final var body = new ErrorResponse<>(ExceptionCode.USED_TOKEN, getExceptionCodeMessage(ex.getCode()));

        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationExceptionHandler(ConstraintViolationException ex, WebRequest request) {
        var cause = ex.getCause().getCause();
        final var body = new ErrorResponse<>(
                ExceptionCode.API_FIELDS_INVALID,
                Map.of("sourceCause", Objects.nonNull(cause.getMessage()) ? cause.getMessage() : ex.getMessage())
        );

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> illegalArgumentExceptionHandler(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse<?> body;

        if (ex.getCause().getClass().equals(UnrecognizedPropertyException.class)) {
            body = new ErrorResponse<>(ExceptionCode.JSON_MAPPING_ERROR, getExceptionCodeMessage(ExceptionCode.JSON_MAPPING_ERROR));

            log.error(ex.getMessage(), ex);
            return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        }

        body = new ErrorResponse<>(ExceptionCode.API_FIELDS_INVALID, Map.of("sourceMessage", ex.getMessage()));
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
