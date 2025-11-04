package cl.duoc.userms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.userms.entities.User;

// Repository layer for User
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByRut(String rut);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByRut(String rut);
}
