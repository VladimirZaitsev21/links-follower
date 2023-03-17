package ru.tinkoff.edu.java.linkparser.parser.impl;

import ru.tinkoff.edu.java.linkparser.model.UserAndRepo;
import ru.tinkoff.edu.java.linkparser.model.answer.GitHubUrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.UrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.api.CommonUrlParser;

import java.net.URI;
import java.util.regex.Pattern;

public final class GitHubUrlParser extends CommonUrlParser {

    public static final Pattern PATTERN = Pattern.compile("/(.+)/(.+)");
    public static final int USER_REGEXP_GROUP_NUMBER = 1;
    public static final int REPO_REGEXP_GROUP_NUMBER = 2;

    public GitHubUrlParser(String processedAuthority) {
        super(processedAuthority, PATTERN);
    }

    @Override
    protected UrlParserAnswer extractPayloadFromUrl(URI parsedUri) {
        var matcher = pattern.matcher(parsedUri.getPath());
        if (matcher.find()) return new GitHubUrlParserAnswer(
                new UserAndRepo(matcher.group(USER_REGEXP_GROUP_NUMBER), matcher.group(REPO_REGEXP_GROUP_NUMBER))
        );
        else return null;
    }
}
