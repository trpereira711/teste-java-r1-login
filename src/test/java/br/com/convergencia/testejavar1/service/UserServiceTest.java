package br.com.convergencia.testejavar1.service;

import br.com.convergencia.testejavar1.controller.form.UserForm;
import br.com.convergencia.testejavar1.gateway.CpfGateway;
import br.com.convergencia.testejavar1.infrastructure.repository.UserRepository;
import br.com.convergencia.testejavar1.model.UserModel;
import br.com.convergencia.testejavar1.model.dto.CpfDto;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    CpfGateway gateway;

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("should register new user account")
    void shouldCreateNewAccountUser() {
        // given
        given(gateway.getByCpf(any())).willReturn(null);

        given(gateway.create(any())).willReturn(new CpfDto("02370453117", "ACTIVE"));

        given(passwordEncoder.encode(any())).willReturn("hash!@@#$%!!");

        given(repository.save(any())).willReturn(new UserModel(1L, "02370453117", "hash!@@#$%!!"));

        // when
        UserDto userDto = userService.registerNewUserAccount(new UserDto(new UserForm("02370453117", "rt545454POL!@#")));

        //then
        assertNotNull(userDto);
        assertEquals(1, userDto.id());
        assertEquals("02370453117", userDto.login());
        assertEquals("none", userDto.password());
    }

}