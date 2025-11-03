package cl.duoc.userms.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.userms.entities.LoginRequest;
import cl.duoc.userms.entities.User;

// Service layer for User
public interface UserService {
    List<User> getAll();

    Optional<User> getById(Long id);

    User saveUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User loginUser(LoginRequest userRequest);
}
