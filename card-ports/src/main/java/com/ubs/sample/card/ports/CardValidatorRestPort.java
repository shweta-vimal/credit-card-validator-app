package com.ubs.sample.card.ports;

import com.ubs.sample.card.domain.CreditCard;
import org.springframework.http.ResponseEntity;

/**
 * Port for incoming requests to validate Credit Card
 */
public interface CardValidatorRestPort {
    ResponseEntity<Boolean> validate(CreditCard creditCard);
}