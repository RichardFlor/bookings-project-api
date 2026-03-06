package br.richard.bookingsproject.errors.i18n;

import br.richard.bookingsproject.errors.ExceptionCode;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageService {
    private final MessageSourceAccessor accessor;

    MessageService() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");

        accessor = new MessageSourceAccessor(messageSource, new Locale("pt", "BR"));
    }

    public String get(String code) {
        return accessor.getMessage(code, new Locale("pt", "BR"));
    }

    public String get(ExceptionCode messageProp) {
        return accessor.getMessage(messageProp.toString(), new Locale("pt", "BR"));
    }

    public String get(ExceptionCode messageProp, String... args) {
        return accessor.getMessage(messageProp.toString(), args);
    }

    public String get(String code, String[] args) {
        return accessor.getMessage(code, args);
    }

    public String get(MessageSourceResolvable resolvable) {
        return accessor.getMessage(resolvable, new Locale("pt", "BR"));
    }
}
