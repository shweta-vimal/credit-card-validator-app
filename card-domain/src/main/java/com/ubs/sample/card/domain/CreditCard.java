package com.ubs.sample.card.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Domain object for Credit Card Details
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCard {
    @NotBlank(message = "Credit Card Number is required")
    @Pattern(regexp = "[\\d\\s]+", message = "Credit Card Number can only have digits and spaces")
    private String cardNumber;
}