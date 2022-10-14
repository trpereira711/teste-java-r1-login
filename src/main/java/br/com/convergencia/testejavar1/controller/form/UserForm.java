package br.com.convergencia.testejavar1.controller.form;

import org.hibernate.validator.constraints.br.CPF;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record UserForm(@NotBlank @CPF(message = "invalid cpf.") String login,
                       @Pattern(regexp = "^(?=.*[@!#$%^&*()/\\\\])[@!#$%^&*()/\\\\a-zA-Z0-9]{8,20}$", message = "password does not meet minimum security requirements.") String password) {

}
