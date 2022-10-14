package br.com.convergencia.testejavar1.model.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenDtoTest {


    @Test
    @DisplayName("should create a dto token object with default type")
    void shouldCreateTokenDto() {
        // when
        TokenDto tokenDto = new TokenDto("abc123");

        // then
        assertEquals("Barear", tokenDto.type());
    }

    @Test
    @DisplayName("should validate non-existent token type")
    void shouldValidateTokenType() {
        // when
        TokenDto tokenDto = new TokenDto("abc123");

        //then
        assertNotEquals("OAuth 2.0", tokenDto.type());
    }
}