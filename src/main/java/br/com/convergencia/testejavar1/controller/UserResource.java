package br.com.convergencia.testejavar1.controller;

import br.com.convergencia.testejavar1.controller.form.UserForm;
import br.com.convergencia.testejavar1.infrastructure.repository.UserRepository;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import br.com.convergencia.testejavar1.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserResource {

    private final UserService service;

    private final UserRepository repository;

    public UserResource(UserService loginService, UserRepository repository) {
        this.service = loginService;
        this.repository = repository;
    }

    @Transactional
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> registerNewUserAccount(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {

        UserDto newUserDto = service.registerNewUserAccount(new UserDto(form));

        URI uri = uriBuilder.path("/users/{id}").build(newUserDto.id());

        return ResponseEntity.created(uri).body(newUserDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return repository.findById(id)
                .map(user -> ResponseEntity.ok(UserDto.convert(user)))
                .orElse(ResponseEntity.notFound().build());
    }
}
