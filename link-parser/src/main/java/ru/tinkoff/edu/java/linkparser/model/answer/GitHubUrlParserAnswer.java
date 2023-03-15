package ru.tinkoff.edu.java.linkparser.model.answer;

import ru.tinkoff.edu.java.linkparser.model.UserAndRepo;

public record GitHubUrlParserAnswer(UserAndRepo userAndRepo) implements UrlParserAnswer {
}
