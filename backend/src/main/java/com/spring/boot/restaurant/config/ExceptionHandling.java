package com.spring.boot.restaurant.config;

import com.spring.boot.restaurant.dto.ExceptionDto;
import com.spring.boot.restaurant.models.BundleMessage;
import com.spring.boot.restaurant.services.BundleTranslationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationException(
            MethodArgumentNotValidException exception) {

        FieldError fieldError =
                exception.getBindingResult()
                        .getFieldErrors()
                        .get(0);

        String error = fieldError.getDefaultMessage();

        BundleMessage bundleMessage =
                BundleTranslationService
                        .getBundleMessageWithArAndEn(error);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new ExceptionDto(bundleMessage)
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(
            Exception exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ExceptionDto(
                                BundleTranslationService
                                        .getBundleMessageWithArAndEn(
                                                exception.getMessage()
                                        )
                        )
                );
    }
}