package br.com.convergencia.testejavar1.gateway.exception;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;

public class GatewayException extends RuntimeException {

    private final ExceptionTypeEnum type;

    public GatewayException(ExceptionTypeEnum type) {
        super("");
        this.type = type;
    }

    public GatewayException(ExceptionTypeEnum type, String message) {
        super(message);
        this.type = type;
    }

    public ExceptionTypeEnum getType() {
        return type;
    }
}
