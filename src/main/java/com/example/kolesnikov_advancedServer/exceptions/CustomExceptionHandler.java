package com.example.kolesnikov_advancedServer.exceptions;

import com.example.kolesnikov_advancedServer.dtos.CustomSuccessResponse;
import com.example.kolesnikov_advancedServer.validations.ErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolationException;

import static com.example.kolesnikov_advancedServer.validations.ErrorCodes.findByMessage;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handlerException(ConstraintViolationException exception) {
        Integer[] errors = exception.getConstraintViolations()
                .stream()
                .map(err -> findByMessage(err.getMessage()).getCode())
                .toArray(Integer[]::new);
        return new ResponseEntity<>(CustomSuccessResponse.badRequest(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleException(MethodArgumentNotValidException ex) {
        Integer[] errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> findByMessage(error.getDefaultMessage()).getCode())
                .toArray(Integer[]::new);
        return new ResponseEntity<>(CustomSuccessResponse.badRequest(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity handleException(NullPointerException e) {
        return new ResponseEntity(CustomSuccessResponse.badRequest(new Integer[]{findByMessage(ErrorCodes.AUTH_IS_NULL.getMessage()).getCode()}), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleException(CustomException exception) {
        Integer[] errors = {findByMessage(exception.getMessage()).getCode()};
        return new ResponseEntity<>(CustomSuccessResponse.badRequest(errors), HttpStatus.BAD_REQUEST);
    }
}
