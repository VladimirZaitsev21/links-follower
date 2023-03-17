package ru.tinkoff.edu.java.linkparser.parser.impl;

import ru.tinkoff.edu.java.linkparser.model.answer.StackOverflowUrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.UrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.api.CommonUrlParser;

import java.net.URI;
import java.util.regex.Pattern;

public final class StackOverflowUrlParser extends CommonUrlParser {

    public static final Pattern PATTERN = Pattern.compile("/(questions)/(\\d{1,20})");
    public static final int QUESTION_ID_REGEXP_GROUP_NUMBER = 2;

    public StackOverflowUrlParser(String processedAuthority) {
        super(processedAuthority, PATTERN);
    }

    @Override
    protected UrlParserAnswer extractPayloadFromUrl(URI parsedUri) {
        var matcher = pattern.matcher(parsedUri.getPath());
        if (matcher.find()) return new StackOverflowUrlParserAnswer(
                Long.parseLong(matcher.group(QUESTION_ID_REGEXP_GROUP_NUMBER))
        );
        else return null;
    }
}
