package ru.tinkoff.edu.java.linkparser.parser.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.edu.java.linkparser.model.answer.StackOverflowUrlParserAnswer;
import ru.tinkoff.edu.java.linkparser.parser.api.UrlParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StackOverflowUrlParserTest {

    @Mock
    private UrlParser mock;

    private StackOverflowUrlParser instance;

    @BeforeEach
    public void initInstance() {
        instance = new StackOverflowUrlParser("stackoverflow.com");
        instance.setNext(mock);
    }

    @Test
    public void parse_shouldReturnIdForCorrectUrl() {
        var expected = new StackOverflowUrlParserAnswer(1642028);
        var actual = instance.parse("https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c");
        assertEquals(expected, actual);
        verifyNoInteractions(mock);
    }

    @Test
    public void parse_shouldReturnNullForIncorrectStackOverflowLink() {
        var actual = instance.parse("https://stackoverflow.com/questions");
        assertNull(actual);
        verifyNoInteractions(mock);
    }

    @Test
    public void parse_shouldSkipParsingForWrongAuthority() {
        var expected = new StackOverflowUrlParserAnswer(1642028);
        when(mock.parse(anyString())).thenReturn(expected);
        var actual = instance.parse("https://github.com/VladimirZaitsev21/some-repo");
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
        assertNull(instance.parse("ht://stackoverflow.com/questions/1642028/what-is-the-operator-in-c"));
    }
}