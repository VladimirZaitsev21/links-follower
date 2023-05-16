package ru.tinkoff.edu.java.scrapper.domain.jdbc.util;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class QueriesSource {

    private final MessageSource messageSource;

    public QueriesSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getQuery(String key) {
        return messageSource.getMessage(key, null, Locale.US);
    }
}
