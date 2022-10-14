package br.com.convergencia.testejavar1.controller;

import br.com.convergencia.testejavar1.controller.form.UserForm;
import br.com.convergencia.testejavar1.model.dto.TokenDto;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import br.com.convergencia.testejavar1.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationResource {

    private final AuthenticationService service;

    public AuthenticationResource(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TokenDto> authenticate(@RequestBody @Valid UserForm form) {
        TokenDto tokenDto = service.authenticate(new UserDto(form));
        return ResponseEntity.ok(tokenDto);
    }
}
