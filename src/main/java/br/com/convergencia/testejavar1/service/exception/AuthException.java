package br.com.convergencia.testejavar1.service.exception;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;
import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

    private final ExceptionTypeEnum type;

    public AuthException(ExceptionTypeEnum type) {
        super("");
        this.type = type;
    }

    public AuthException(ExceptionTypeEnum type, String message) {
        super(message);
        this.type = type;
    }

    public ExceptionTypeEnum getType() {
        return type;
    }
}
