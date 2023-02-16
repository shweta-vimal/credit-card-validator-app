package com.ubs.sample.card.services.internal;

import com.ubs.sample.card.domain.exception.CardNumberNonNumericException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Service to perform Credit Card validations
 */
@Service
public class CardValidatorService {
    private static final String SPACE_REGEX = "\\s+";

    /**
     * Validate Credit Card Number using Luhn algorithm
     *
     * @param cardNumber - Card Number expected to be numeric but can have spaces
     */
    public Boolean validateCardNumber(String cardNumber) {
        Objects.requireNonNull(cardNumber, "Card number is Required");
        String digits = cardNumber.replaceAll(SPACE_REGEX, "");
        if (digits.isBlank() || !digits.chars().allMatch(Character::isDigit)) {
            throw new CardNumberNonNumericException("Credit Card Number can only have digits and spaces");
        }

        int sum = 0;
        boolean skip = false;
        for (int i = digits.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(digits.charAt(i));
            if (skip) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            skip = !skip;
        }
        return (sum % 10 == 0);
    }
}