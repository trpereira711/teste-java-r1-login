package br.com.convergencia.testejavar1.service;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;
import br.com.convergencia.testejavar1.infrastructure.repository.UserRepository;
import br.com.convergencia.testejavar1.model.UserModel;
import br.com.convergencia.testejavar1.model.dto.CpfDto;
import br.com.convergencia.testejavar1.model.dto.TokenDto;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import br.com.convergencia.testejavar1.model.enums.StatusEnum;
import br.com.convergencia.testejavar1.service.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository repository;

    private final PasswordEncoder encoder;

    private final TokenService tokenService;

    private final CpfFindService cpfFindService;

    public AuthenticationService(UserRepository repository, PasswordEncoder encoder,
                                 TokenService tokenService, CpfFindService cpfFindService) {
        this.repository = repository;
        this.encoder = encoder;
        this.tokenService = tokenService;
        this.cpfFindService = cpfFindService;
    }

    public TokenDto authenticate(UserDto userDto) {
        Optional<UserModel> optionalUser = repository.findByLogin(userDto.login().replaceAll("[.-]", ""));

        if (optionalUser.isEmpty()) {
            logger.info("method=authenticate, message=user {} not found", userDto.login());
            throw new AuthException(ExceptionTypeEnum.USER_NOT_FOUND, "check your username.");
        }

        CpfDto foundCpf = cpfFindService.getByCpf(userDto.login());

        if (StatusEnum.INACTIVE.name().equals(foundCpf.status())) {
            logger.info("method=authenticate, message=user {} is inactive", userDto.login());
            throw new AuthException(ExceptionTypeEnum.INACTIVE_USER, "activate your user.");
        }

        UserModel foundUser = optionalUser.get();
        boolean isValid = encoder.matches(userDto.password(), foundUser.getPassword());

        if (!isValid) {
            logger.info("method=authenticate, message=user or password is incorrect");
            throw new AuthException(ExceptionTypeEnum.INVALID_USER, "check your password.");
        }

        logger.info("method=authenticate, message=successfully authenticated");
        String token = tokenService.generate(foundUser);
        logger.info("method=authenticate, message=token generated successfully");

        return new TokenDto(token);
    }
}
