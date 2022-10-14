package br.com.convergencia.testejavar1.infrastructure.repository;

import br.com.convergencia.testejavar1.model.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;


    @Test
    @DisplayName("should find user by login")
    void shouldFindUserByLogin() {
        // given
        UserModel userModel = new UserModel("02370453117", "hash123!@#");
        repository.save(userModel);

        // and
        String login = "02370453117";

        // when
        UserModel foundUser = repository.findByLogin(login).get();

        // then
        assertNotNull(foundUser);
        assertEquals("02370453117", foundUser.getLogin());
        assertEquals("hash123!@#", foundUser.getPassword());

    }
}