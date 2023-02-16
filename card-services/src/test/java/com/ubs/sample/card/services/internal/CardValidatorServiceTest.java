package com.ubs.sample.card.services.internal;

import com.ubs.sample.card.domain.exception.CardNumberNonNumericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test for {@link CardValidatorService}
 */
public class CardValidatorServiceTest {
    private CardValidatorService cardValidatorService;
    @BeforeEach
    public void init() {
        cardValidatorService = new CardValidatorService();
    }
    @Test
    void testValidateCardNumber() {
        String input = "4417 1234 5678 9113";
        boolean result = cardValidatorService.validateCardNumber(input);
        assertTrue(result);
    }
    @Test
    void testValidateCardNumberInvalid() {
        String input = "4417 1234 5678 9223";
        boolean result = cardValidatorService.validateCardNumber(input);
        assertFalse(result);
    }
    @Test
    void testValidateCardNumberNonNumeric() {
        String input = "4417 XXXX 5678 9223";
        assertThrows(CardNumberNonNumericException.class, () -> cardValidatorService.validateCardNumber(input));
    }
    @Test
    void testValidateCardNumberNull() {
        assertThrows(NullPointerException.class, () -> cardValidatorService.validateCardNumber(null));
    }
    @Test
    void testValidateCardNumberBlank() {
        assertThrows(CardNumberNonNumericException.class, () -> cardValidatorService.validateCardNumber("   "));
    }
}
