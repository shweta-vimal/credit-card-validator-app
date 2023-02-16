package com.ubs.sample.card.services.external;

import com.ubs.sample.card.domain.exception.CardNumberNonNumericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Function<WebRequest, List<String>> EXTRACT_REQUEST_DETAILS = request1 -> Arrays.stream(request1.getDescription(true).split(";")).collect(Collectors.toList());

    @ExceptionHandler(value = CardNumberNonNumericException.class)
    public ResponseEntity<Boolean> handleCardNumberNonNumericException(CardNumberNonNumericException ex, WebRequest request) {
        log.warn("Rest request [{}] failed due to {}. Request: {}", EXTRACT_REQUEST_DETAILS.apply(request), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Boolean> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        log.warn("Rest request [{}] failed due to {}. Request: {}", EXTRACT_REQUEST_DETAILS.apply(request), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Boolean> handleCommonException(Exception ex, ServletWebRequest request) {
        log.warn("Rest request [{}] failed due to {}. Request: {}", EXTRACT_REQUEST_DETAILS.apply(request), ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
    }
}