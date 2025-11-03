package cl.duoc.userms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.userms.entities.User;

// Repository layer for User
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to find an user by email and password
    User findByEmailAndPassword(String email, String password);
}
