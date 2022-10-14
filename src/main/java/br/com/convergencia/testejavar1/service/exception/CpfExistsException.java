package br.com.convergencia.testejavar1.service.exception;

import br.com.convergencia.testejavar1.config.validation.ExceptionTypeEnum;

public class CpfExistsException extends RuntimeException {

    private final ExceptionTypeEnum type;

    public CpfExistsException(ExceptionTypeEnum type) {
        super("");
        this.type = type;
    }

    public CpfExistsException(ExceptionTypeEnum type, String message) {
        super(message);
        this.type = type;
    }

    public ExceptionTypeEnum getType() {
        return type;
    }
}
