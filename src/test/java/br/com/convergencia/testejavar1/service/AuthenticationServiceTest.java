package br.com.convergencia.testejavar1.service;

import br.com.convergencia.testejavar1.infrastructure.repository.UserRepository;
import br.com.convergencia.testejavar1.model.UserModel;
import br.com.convergencia.testejavar1.model.dto.CpfDto;
import br.com.convergencia.testejavar1.model.dto.TokenDto;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    UserRepository repositoryMck;

    @Mock
    PasswordEncoder encoderMck;

    @Mock
    TokenService tokenServiceMck;

    @Mock
    CpfFindService cpfFindServiceMck;

    @InjectMocks
    AuthenticationService service;

    @Test
    @DisplayName("should authenticate")
    void shouldAuthenticate() {
        // given
        UserDto userDto = new UserDto(1L, "02370453117", "dt213656PPO!@#$");

        given(repositoryMck.findByLogin(any()))
                .willReturn(Optional.of(new UserModel(1L, "02370453117", "dt213656PPO!@#$")));

        given(cpfFindServiceMck.getByCpf(any()))
                .willReturn(new CpfDto("02370453117", "ACTIVE"));

        given(encoderMck.matches(any(), any())).willReturn(Boolean.TRUE);

        given(tokenServiceMck.generate(any())).willReturn("token1234");

        // when
        TokenDto tokenDto = service.authenticate(new UserDto(1L, "02370453117", "dt213656PPO!@#$"));

        // then
        assertNotNull(tokenDto);
        assertEquals("token1234", tokenDto.token());
        assertEquals("Barear", tokenDto.type());
    }

}