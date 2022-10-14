package br.com.convergencia.testejavar1.infrastructure.repository;

import br.com.convergencia.testejavar1.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByLogin(String login);
}
