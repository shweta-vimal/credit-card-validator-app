package com.ubs.sample.card.services.external;

import com.ubs.sample.card.services.internal.CardValidatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CreditCardController}
 */
@ExtendWith(MockitoExtension.class)
public class CreditCardControllerTest {
    @Mock
    private CardValidatorService cardValidatorService;
    private MockMvc mockMvc;
    @InjectMocks
    private CreditCardController creditCardController;
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(creditCardController).build();
    }
    @Test
    void testValidate() throws Exception {
        when(cardValidatorService.validateCardNumber("1234 5678")).thenReturn(true);
        String testPayload = "{\"cardNumber\":\"1234 5678\"}";
        ResultActions result = this.mockMvc.perform(post("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testPayload));

        result.andExpect(status().isOk()).andExpect(content().string("true"));
        verify(cardValidatorService).validateCardNumber("1234 5678");
    }
    @Test
    void testValidateInvalidCardNumber() throws Exception {
        when(cardValidatorService.validateCardNumber("1234")).thenReturn(false);
        String testPayload = "{\"cardNumber\":\"1234\"}";

        ResultActions result = this.mockMvc.perform(post("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testPayload));

        result.andExpect(status().isOk()).andExpect(content().string("false"));
        verify(cardValidatorService).validateCardNumber("1234");
    }
    @Test
    void testValidateCardNumberWithAlphabets() throws Exception {
        String testPayload = "{\"cardNumber\":\"1234 XXXX\"}";

        ResultActions result = this.mockMvc.perform(post("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testPayload));

        result.andExpect(status().isBadRequest());
        verify(cardValidatorService, never()).validateCardNumber("1234 XXXX");
    }

    @Test
    void testValidateCardNumberNull() throws Exception {
        ResultActions result = this.mockMvc.perform(post("/api/credit-card/validate"));

        result.andExpect(status().isBadRequest());
        verify(cardValidatorService, never()).validateCardNumber(anyString());
    }

    @Test
    void testValidateBlankCardNumber() throws Exception {
        String testPayload = "{\"cardNumber\":\"     \"}";

        ResultActions result = this.mockMvc.perform(post("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testPayload));

        result.andExpect(status().isBadRequest());
        verify(cardValidatorService, never()).validateCardNumber(anyString());
    }
}
