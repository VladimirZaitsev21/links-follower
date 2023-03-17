package ru.tinkoff.edu.java.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IncorrectRequestParamsException extends RuntimeException {

    private final String description;
}
