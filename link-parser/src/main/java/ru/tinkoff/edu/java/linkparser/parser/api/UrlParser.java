package ru.tinkoff.edu.java.linkparser.parser.api;

import ru.tinkoff.edu.java.linkparser.model.answer.UrlParserAnswer;

public interface UrlParser {

    void setNext(UrlParser next);

    UrlParserAnswer parse(String uri);
}
