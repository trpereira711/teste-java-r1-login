package br.com.convergencia.testejavar1.model.dto;

public record TokenDto(String token, String type) {

    public TokenDto (String token) {
        this(token, "Barear");
    }

}
