package br.richard.bookingsproject.errors.exceptions;

import br.richard.bookingsproject.errors.ExceptionCode;
import jakarta.mail.MessagingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Slf4j
public class EmailNotSentException extends RuntimeException {
    private final MessagingException messagingException;
    private final ExceptionCode code = ExceptionCode.EMAIL_NOT_SENT;
    private final List<String> to;

    public EmailNotSentException(List<String> to, MessagingException ex) {
        super(ExceptionCode.EMAIL_NOT_SENT.toString());
        this.messagingException = ex;
        this.to = to;

        log.error(this.getMessage(), this);
    }
}
