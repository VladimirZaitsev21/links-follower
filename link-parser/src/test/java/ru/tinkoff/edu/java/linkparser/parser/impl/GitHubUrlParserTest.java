package ru.tinkoff.edu.java.linkparser.parser.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.linkparser.model.UserAndRepo;
import ru.tinkoff.edu.java.linkparser.model.answer.GitHubUrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.model.answer.StackOverflowUrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.api.UrlParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GitHubUrlParserTest {

    @Mock
    private UrlParser mock;
    private GitHubUrlParser instance;

    @BeforeEach
    public void initInstance() {
        instance = new GitHubUrlParser("github.com");
        instance.setNext(mock);
    }

    @Test
    public void parse_shouldReturnUserAndRepoForCorrectUrl() {
        var expected = new GitHubUrlParserAnswer(new UserAndRepo("VladimirZaitsev21", "some-repo"));
        var actual = instance.parse("https://github.com/VladimirZaitsev21/some-repo");
        assertEquals(expected, actual);
        verifyNoInteractions(mock);
    }

    @Test
    public void parse_shouldReturnNullForIncorrectGitHubLink() {
        var actual = instance.parse("https://github.com/VladimirZaitsev21");
        assertNull(actual);
        verifyNoInteractions(mock);
    }

    @Test
    public void parse_shouldSkipParsingForWrongAuthority() {
        var expected = new StackOverflowUrlParserAnswer(1642028);
        when(mock.parse(anyString())).thenReturn(expected);
        var actual = instance.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        assertEquals(expected, actual);
        verify(mock, times(1)).parse(anyString());
    }

    @Test
    public void parse_shouldReturnNullForNull() {
        assertNull(instance.parse(null));
        verifyNoInteractions(mock);
    }

    @Test
    public void parse_shouldReturnNullForMalformedUrl() {
        assertNull(instance.parse("http://github.com/VladimirZaitsev21/some-repo java"));
    }
}