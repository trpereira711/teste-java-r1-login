package br.com.convergencia.testejavar1.model.dto;


import br.com.convergencia.testejavar1.controller.form.UserForm;
import br.com.convergencia.testejavar1.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;

public record UserDto(Long id, String login, String password) {

    public UserDto(UserForm form) {
        this(null, form.login(), form.password());
    }

    public UserModel convert(PasswordEncoder encoder) {
        return new UserModel(login.replaceAll("[.-]", ""), encoder.encode(password));
    }

    public static UserDto convert(UserModel user) {
        return new UserDto(user.getId(), user.getLogin(), "none");
    }

}
