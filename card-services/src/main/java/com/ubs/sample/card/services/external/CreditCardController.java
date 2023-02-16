package com.ubs.sample.card.services.external;

import com.ubs.sample.card.domain.CreditCard;
import com.ubs.sample.card.ports.CardValidatorRestPort;
import com.ubs.sample.card.services.internal.CardValidatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Rest endpoints for Credit Card Validation
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/credit-card")
@Tag(name = "Operation to perform Credit Card Validation")
public class CreditCardController implements CardValidatorRestPort {
    private final CardValidatorService cardValidatorService;

    @Override
    @PostMapping("/validate")
    @Operation(summary = "Validate Credit Card Number")
    public ResponseEntity<Boolean> validate(@Valid @RequestBody CreditCard creditCard) {
        return ResponseEntity.ok(cardValidatorService.validateCardNumber(creditCard.getCardNumber()));
    }
}
