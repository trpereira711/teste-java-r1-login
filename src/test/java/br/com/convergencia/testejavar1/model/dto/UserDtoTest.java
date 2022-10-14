package br.com.convergencia.testejavar1.model.dto;

import br.com.convergencia.testejavar1.controller.form.UserForm;
import br.com.convergencia.testejavar1.model.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDtoTest {

    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

    @Test
    @DisplayName("should create dto user object from user form object")
    void shouldCreateUserToDtoFromUserForm() {
        // given
        UserForm userForm = new UserForm("02370453117", "dt213656PPO!@#$");

        // when
        UserDto userDto = new UserDto(userForm);

        // then
        assertEquals(userForm.login(), userDto.login());
        assertEquals(userForm.password(), userDto.password());
        assertNull(userDto.id());
    }

    @Test
    @DisplayName("should convert to user model object")
    void shouldConvertToUserModel() {
        // given
        UserDto userDto = new UserDto(null, "023.704.531-17", "dt213656PPO!@#$");
        when(passwordEncoder.encode(userDto.password())).thenReturn("hash1234!@#");

        // when
        UserModel userModel = userDto.convert(passwordEncoder);

        // then
        assertEquals("02370453117", userModel.getLogin());
        assertEquals("hash1234!@#", userModel.getPassword());
    }

    @Test
    @DisplayName("should convert to user dto object")
    void shouldConvertToUserDto() {
        // given
        UserModel userModel = new UserModel(1L, "02370453117", "hash1234!@#");

        // when
        UserDto userDto = UserDto.convert(userModel);

        // then
        assertEquals(1L, userDto.id());
        assertEquals("02370453117", userDto.login());
        assertEquals("none", userDto.password());
    }

}