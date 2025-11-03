package cl.duoc.userms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.userms.entities.LoginRequest;
import cl.duoc.userms.entities.User;
import cl.duoc.userms.repository.UserRepository;
import lombok.RequiredArgsConstructor;

// Service layer to manage business logic
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    // Override get all users method with the business logic
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // Override get user by id method with the business logic
    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    // Override save user method with the business logic
    @Override
    public User saveUser(User user) {
        try {
            User savedUser = userRepository.save(user);

            return savedUser;
        } catch (Exception e) {
            throw e;
        }
    }

    // Override update user method with the business logic
    @Override
    public User updateUser(Long id, User user) {
        try {
            Optional<User> existingUserOpt = userRepository.findById(id);

            if (existingUserOpt.isPresent()) {
                User updatedUser = existingUserOpt.get();

                updatedUser.setName(user.getName());
                updatedUser.setEmail(user.getEmail());
                updatedUser.setPassword(user.getPassword());
                updatedUser.setPhone(user.getPhone());
                updatedUser.setBirthDate(user.getBirthDate());
                updatedUser.setUpdatedAt(LocalDateTime.now());

                return userRepository.save(updatedUser);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // Override delete user method with the business logic
    @Override
    public void deleteUser(Long id) {
        try {
            Optional<User> existingUserOpt = userRepository.findById(id);

            if (existingUserOpt.isPresent()) {
                userRepository.delete(existingUserOpt.get());
            } else {
                throw new RuntimeException("User not found");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    // Override login user method with the business logic
    @Override
    public User loginUser(LoginRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        if (email == null || password == null) {
            throw new RuntimeException("Email or password is null");
        }
        try {
            User user = userRepository.findByEmailAndPassword(email, password);

            if (user == null) {
                throw new RuntimeException("Credentials are not valid");
            }

            return user;
        } catch (Exception e) {
            throw e;
        }
    }
}
