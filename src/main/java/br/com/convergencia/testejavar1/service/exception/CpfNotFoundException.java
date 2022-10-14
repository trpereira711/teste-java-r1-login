package br.com.convergencia.testejavar1.service.exception;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;

public class CpfNotFoundException extends RuntimeException {
    private final ExceptionTypeEnum type;

    public CpfNotFoundException(ExceptionTypeEnum type) {
        super("");
        this.type = type;
    }

    public CpfNotFoundException(ExceptionTypeEnum type, String message) {
        super(message);
        this.type = type;
    }

    public ExceptionTypeEnum getType() {
        return type;
    }
}
