package INFSUS.project.PRO.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import INFSUS.project.PRO.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
