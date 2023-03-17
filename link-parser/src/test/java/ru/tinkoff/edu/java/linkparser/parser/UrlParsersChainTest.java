package ru.tinkoff.edu.java.linkparser.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.tinkoff.edu.java.linkparser.model.UserAndRepo;
import ru.tinkoff.edu.java.linkparser.model.answer.GitHubUrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.StackOverflowUrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.UrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.api.UrlParser;
import ru.tinkoff.edu.java.linkparser.parser.impl.GitHubUrlParser;
import ru.tinkoff.edu.java.linkparser.parser.impl.StackOverflowUrlParser;
import ru.tinkoff.edu.java.linkparser.parser.impl.UnsupportedUrlParser;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UrlParsersChainTest {

    private final UrlParser gitHubUrlParser = new GitHubUrlParser("github.com");
    private final UrlParser stackOverflowUrlParser = new StackOverflowUrlParser("stackoverflow.com");
    private final UrlParser unsupportedUrlParser = new UnsupportedUrlParser();

    private final UrlParsersChain parsersChain = new UrlParsersChain(gitHubUrlParser, stackOverflowUrlParser, unsupportedUrlParser);

    @ParameterizedTest
    @MethodSource("getArgumentsForDoParseTest")
    public void testDoParse(String source, UrlParserAnswer expected) {
        var actual = parsersChain.doParse(source);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> getArgumentsForDoParseTest() {
        return Stream.of(
                Arguments.of(
                    "https://github.com/VladimirZaitsev21/some-repo",
                    new GitHubUrlParserAnswer(new UserAndRepo("VladimirZaitsev21", "some-repo"))
                ),
                Arguments.of(
                    "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c",
                    new StackOverflowUrlParserAnswer(1642028)
                ),
                Arguments.of(
                    "https://github.com/VladimirZaitsev21?tab=repositories",
                    null
                ),
                Arguments.of(
                    "https://stackoverflow.com/questions",
                    null
                ),
                Arguments.of(
                    "https://bitbucket.org/VladimirZaitsev21/some-repo",
                    null
                ),
                Arguments.of(
                    "htt://stackoverflow.com/questions/1642028/what-is-the-operator-in-c java",
                    null
                ),
                Arguments.of(
                    null,
                    null
                )
        );
    }
}