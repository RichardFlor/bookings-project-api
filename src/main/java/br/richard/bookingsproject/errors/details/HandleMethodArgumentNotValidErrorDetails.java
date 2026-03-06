package br.richard.bookingsproject.errors.details;

public record HandleMethodArgumentNotValidErrorDetails(
        String field,
        String[] messages
) {
}
