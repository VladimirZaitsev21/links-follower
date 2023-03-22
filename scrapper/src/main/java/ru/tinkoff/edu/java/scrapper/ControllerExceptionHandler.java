package ru.tinkoff.edu.java.scrapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.common.exception.IncorrectRequestParamsException;
import ru.tinkoff.edu.java.common.model.ApiErrorResponse;
import ru.tinkoff.edu.java.scrapper.exception.ResourceNotFoundException;

import java.util.Arrays;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ApiErrorResponse(
                "Requested resource not found",
                String.valueOf(HttpStatus.NOT_FOUND.value()),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(String::valueOf).toList()
        );
    }

    @ExceptionHandler({IncorrectRequestParamsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleIncorrectRequestParamsException(IncorrectRequestParamsException exception) {
        return new ApiErrorResponse(
                "There are incorrect query parameters/body",
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(String::valueOf).toList()
        );
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handle(MissingServletRequestParameterException exception) {
        return new ApiErrorResponse(
                "There are missing query parameters!",
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                exception.getClass().getName(),
                exception.getMessage(),
                Arrays.stream(exception.getStackTrace()).map(String::valueOf).toList()
        );
    }
}
