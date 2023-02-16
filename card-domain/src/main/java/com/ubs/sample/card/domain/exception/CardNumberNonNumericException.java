package com.ubs.sample.card.domain.exception;


/**
 * Represents an exception when Card Number contains special characters excluding numbers and spaces
 */
public class CardNumberNonNumericException extends RuntimeException {

    public CardNumberNonNumericException(String message) {
        super(message);
    }

    public CardNumberNonNumericException(String message, Throwable cause) {
        super(message, cause);
    }
}
