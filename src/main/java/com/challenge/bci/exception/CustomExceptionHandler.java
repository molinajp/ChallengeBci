package com.challenge.bci.exception;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.challenge.bci.dto.exception.ErrorsDto;
import com.challenge.bci.dto.exception.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerMethodExceptionResolver;

@ControllerAdvice
public class CustomExceptionHandler extends AbstractHandlerMethodExceptionResolver {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorsDto> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorsDto errors = new ErrorsDto(new ArrayList<>());
        ExceptionDto errorDetails = new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorsDto> handleRuntimeExceptionExceptions(RuntimeException ex, WebRequest request) {
        ErrorsDto errors = new ErrorsDto(new ArrayList<>());
        ExceptionDto errorDetails = new ExceptionDto(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<ErrorsDto> handleNullPointerExceptions(NullPointerException ex, WebRequest request) {
        ErrorsDto errors = new ErrorsDto(new ArrayList<>());
        ExceptionDto errorDetails = new ExceptionDto(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TokenNotValidException.class)
    public final ResponseEntity<ErrorsDto> handleHttpMessageNotReadableExceptions(TokenNotValidException ex,
            WebRequest request) {
        ErrorsDto errors = new ErrorsDto(new ArrayList<>());
        ExceptionDto errorDetails = new ExceptionDto(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public final ResponseEntity<ErrorsDto> handleMissingRequestHeaderException(MissingRequestHeaderException ex,
            WebRequest request) {
        ErrorsDto errors = new ErrorsDto(new ArrayList<>());
        ExceptionDto errorDetails = new ExceptionDto(new Date(), HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        errors.addException(errorDetails);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorsDto> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex,
            WebRequest request) {
        ErrorsDto errors = new ErrorsDto(new ArrayList<>());
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            ExceptionDto errorDetails = new ExceptionDto(new Date(), HttpStatus.BAD_REQUEST.value(),
                    error.getField() + ": " + error.getDefaultMessage());
            errors.addException(errorDetails);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
            HandlerMethod handlerMethod, Exception ex) {
        return null;
    }
}
