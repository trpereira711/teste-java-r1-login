package br.com.convergencia.testejavar1.config.validation;

public enum ExceptionTypeEnum {

    CPF_FIND_ERROR(1, "remote cpf api = error trying to find cpf"),

    CPF_SAVE_ERROR(2, "remote cpf api = error trying to save cpf"),

    CPF_NOT_FOUND(3, "remote cpf api = CPF not found."),

    CPF_EXISTS(4, "CPF exists"),

    INVALID_USER(5, "invalid user."),

    USER_NOT_FOUND(6, "user not found."),

    INACTIVE_USER(7, "user is disabled");



    private final Integer id;

    private final String message;

    ExceptionTypeEnum(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
