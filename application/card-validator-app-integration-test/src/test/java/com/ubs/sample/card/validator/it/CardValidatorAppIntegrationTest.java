package com.ubs.sample.card.validator.it;

import com.ubs.sample.card.validator.app.CardValidatorApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration Tests
 */
@SpringBootTest(classes = CardValidatorApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardValidatorAppIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testValidateCardNumber() {
        String body = "{\"cardNumber\":\"4417 1234 5678 9113\"}";
        WebTestClient.ResponseSpec response = webTestClient
                .post().uri("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body))
                .exchange();

        response.expectStatus().isOk()
                .expectBody().consumeWith(e ->
                        assertEquals("true", new String(Objects.requireNonNull(e.getResponseBodyContent()), StandardCharsets.UTF_8)));
    }

    @Test
    void testValidateCardNumberInvalid() {
        String body = "{\"cardNumber\":\"4417 1234 5678 93\"}";
        WebTestClient.ResponseSpec response = webTestClient
                .post().uri("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body))
                .exchange();

        response.expectStatus().isOk()
                .expectBody().consumeWith(e ->
                        assertEquals("false", new String(Objects.requireNonNull(e.getResponseBodyContent()), StandardCharsets.UTF_8)));
    }

    @Test
    void testValidateCardNumberWithAlphabets() {
        String body = "{\"cardNumber\":\"4417 XXXX\"}";
        WebTestClient.ResponseSpec response = webTestClient
                .post().uri("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body))
                .exchange();

        response.expectStatus().isBadRequest();
    }

    @Test
    void testValidateCardNumberNull() {
        WebTestClient.ResponseSpec response = webTestClient
                .post().uri("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange();

        response.expectStatus().is5xxServerError();
    }

    @Test
    void testValidateBlankCardNumber() {
        String body = "{\"cardNumber\":\"       \"}";
        WebTestClient.ResponseSpec response = webTestClient
                .post().uri("/api/credit-card/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(body))
                .exchange();

        response.expectStatus().isBadRequest();
    }
}
