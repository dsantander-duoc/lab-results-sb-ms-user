package cl.duoc.userms.service;

import java.util.List;
import java.util.Optional;

import cl.duoc.userms.dto.CreateUserRequest;
import cl.duoc.userms.dto.UserResponse;
import cl.duoc.userms.dto.LoginRequest;
import cl.duoc.userms.entities.User;

// Service layer for User
public interface UserService {
    List<UserResponse> getAll();

    Optional<UserResponse> getById(Long id);

    UserResponse saveUser(CreateUserRequest request);

    UserResponse updateUser(Long id, CreateUserRequest request);

    void deleteUser(Long id);

    UserResponse loginUser(LoginRequest userRequest);
}
