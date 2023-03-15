package ru.tinkoff.edu.java.linkparser.parser;

import ru.tinkoff.edu.java.linkparser.model.answer.UrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.api.UrlParser;

public class UrlParsersChain {

    private final UrlParser parserToStartFrom;

    public UrlParsersChain(UrlParser... urlParsers) {
        parserToStartFrom = urlParsers[0];
        for (int i = 0; i < urlParsers.length - 1; i++) {
            urlParsers[i].setNext(urlParsers[i + 1]);
        }
    }

    public UrlParserAnswer doParse(String url) {
        return parserToStartFrom.parse(url);
    }
}
