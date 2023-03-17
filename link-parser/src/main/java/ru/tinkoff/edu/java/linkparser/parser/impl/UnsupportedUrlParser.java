package ru.tinkoff.edu.java.linkparser.parser.impl;

import ru.tinkoff.edu.java.linkparser.model.answer.UrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.api.UrlParser;

public final class UnsupportedUrlParser implements UrlParser {

    private UrlParser nextParser;
    @Override
    public void setNext(UrlParser next) {
        this.nextParser = next;
    }

    @Override
    public UrlParserAnswer parse(String uri) {
        return nextParser == null ? null : nextParser.parse(uri);
    }
}
