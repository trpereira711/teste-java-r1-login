package br.com.convergencia.testejavar1.controller.form;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserFormTest {

    private static ValidatorFactory validatorFactory;

    private static Validator validator;

    @BeforeAll
    public static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    @DisplayName("should have no violations")
    void shouldHaveNoViolations() {
        // given
        UserForm userForm = new UserForm("02370453117", "dt213656PPO!@#$");

        // when
        Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);

        // then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("should have violations to username")
    void shouldHaveViolationsToUsername() {
        // given
        UserForm userForm = new UserForm("11122233345", "dt213656PPO!@#$");

        // when
        Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);

        // then
        ConstraintViolation<UserForm> violation = violations.iterator().next();
        assertEquals("invalid cpf.", violation.getMessage());
        assertEquals("11122233345", violation.getInvalidValue());
        assertEquals(1, violations.size());
        
    }

    @Test
    @DisplayName("should have violations to password")
    void shouldHaveViolationsToPassword() {
        // given
        UserForm userForm = new UserForm("02370453117", "test1234567");

        // when
        Set<ConstraintViolation<UserForm>> violations = validator.validate(userForm);

        // then
        ConstraintViolation<UserForm> violation = violations.iterator().next();
        assertEquals("password does not meet minimum security requirements.", violation.getMessage());
        assertEquals("test1234567", violation.getInvalidValue());
        assertEquals(1, violations.size());

    }
}