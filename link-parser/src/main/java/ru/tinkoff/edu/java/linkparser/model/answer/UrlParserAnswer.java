package ru.tinkoff.edu.java.linkparser.model.answer;

public sealed interface UrlParserAnswer permits GitHubUrlParserAnswer, StackOverflowUrlParserAnswer {
}
