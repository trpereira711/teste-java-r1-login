package br.com.convergencia.testejavar1.service;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;
import br.com.convergencia.testejavar1.gateway.CpfGateway;
import br.com.convergencia.testejavar1.infrastructure.repository.UserRepository;
import br.com.convergencia.testejavar1.model.UserModel;
import br.com.convergencia.testejavar1.model.dto.CpfDto;
import br.com.convergencia.testejavar1.model.dto.UserDto;
import br.com.convergencia.testejavar1.service.exception.CpfExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final CpfGateway gateway;

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(CpfGateway gateway, UserRepository repository, PasswordEncoder passwordEncoder) {
        this.gateway = gateway;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto registerNewUserAccount(UserDto userDto) throws CpfExistsException {
        CpfDto foundCpf = gateway.getByCpf(userDto.login());

        if (Objects.nonNull(foundCpf)) {
            var foundCpfValue = foundCpf.value();
            logger.info("method=registerNewUserAccount, message=cpf {} was found", foundCpfValue);
            throw new CpfExistsException(ExceptionTypeEnum.CPF_EXISTS);
        }

        CpfDto savedCpf = gateway.create(userDto);
        var savedCpfValue = savedCpf.value();
        logger.info("method=registerNewUserAccount, message=cpf {} registered successfully", savedCpfValue);

        UserModel user = userDto.convert(passwordEncoder);

        UserModel savedUser = repository.save(user);
        logger.info("method=registerNewUserAccount, message=user {} registered successfully", user.getLogin());

        return UserDto.convert(savedUser);
    }
}
