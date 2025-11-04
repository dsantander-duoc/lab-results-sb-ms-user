package cl.duoc.userms.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.duoc.userms.dto.CreateUserRequest;
import cl.duoc.userms.dto.LoginRequest;
import cl.duoc.userms.dto.UserResponse;
import cl.duoc.userms.entities.Comuna;
import cl.duoc.userms.entities.Laboratory;
import cl.duoc.userms.entities.Role;
import cl.duoc.userms.entities.User;
import cl.duoc.userms.repository.ComunaRepository;
import cl.duoc.userms.repository.LaboratoryRepository;
import cl.duoc.userms.repository.RoleRepository;
import cl.duoc.userms.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ComunaRepository comunaRepository;
    private final LaboratoryRepository laboratoryRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAll().stream().map(user -> UserResponse.builder()
                .userId(user.getUserId())
                .rut(user.getRut())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .active(user.isActive())
                .comunaId(user.getComuna() != null ? user.getComuna().getId() : null)
                .laboratoryId(user.getLaboratory() != null ? user.getLaboratory().getLabId() : null)
                .roleIds(user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build()).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponse> getById(Long id) {
        return userRepository.findById(id).map(user -> UserResponse.builder()
                .userId(user.getUserId())
                .rut(user.getRut())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .active(user.isActive())
                .comunaId(user.getComuna() != null ? user.getComuna().getId() : null)
                .laboratoryId(user.getLaboratory() != null ? user.getLaboratory().getLabId() : null)
                .roleIds(user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build());
    }

    @Override
    @Transactional
    public UserResponse saveUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }
        if (userRepository.existsByRut(request.getRut())) {
            throw new IllegalArgumentException("RUT already in use");
        }

        Comuna comuna = comunaRepository.findById(request.getComunaId())
                .orElseThrow(() -> new IllegalArgumentException("Comuna not found"));

        Laboratory lab = null;
        if (request.getLaboratoryId() != null) {
            lab = laboratoryRepository.findById(request.getLaboratoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Laboratory not found"));
        }

        if (request.getRoleIds() == null || request.getRoleIds().isEmpty()) {
            throw new IllegalArgumentException("At least one role is required");
        }
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(request.getRoleIds()));
        if (roles.size() != request.getRoleIds().size()) {
            throw new IllegalArgumentException("One or more roles not found");
        }

        User user = new User();
        user.setRut(request.getRut());
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setBirthDate(request.getBirthDate());
        user.setAddress(request.getAddress());
        user.setActive(request.getActive() != null ? request.getActive() : true);
        user.setComuna(comuna);
        user.setLaboratory(lab);
        user.setRoles(roles);

        User saved = userRepository.save(user);

        return UserResponse.builder()
                .userId(saved.getUserId())
                .rut(saved.getRut())
                .username(saved.getUsername())
                .name(saved.getName())
                .lastName(saved.getLastName())
                .email(saved.getEmail())
                .phone(saved.getPhone())
                .birthDate(saved.getBirthDate())
                .address(saved.getAddress())
                .active(saved.isActive())
                .comunaId(saved.getComuna() != null ? saved.getComuna().getId() : null)
                .laboratoryId(saved.getLaboratory() != null ? saved.getLaboratory().getLabId() : null)
                .roleIds(saved.getRoles().stream().map(Role::getRoleId).collect(java.util.stream.Collectors.toSet()))
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, CreateUserRequest incoming) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (incoming.getEmail() != null && !incoming.getEmail().equalsIgnoreCase(existing.getEmail())) {
            if (userRepository.existsByEmail(incoming.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
            existing.setEmail(incoming.getEmail());
        }
        if (incoming.getUsername() != null && !incoming.getUsername().equalsIgnoreCase(existing.getUsername())) {
            if (userRepository.existsByUsername(incoming.getUsername())) {
                throw new IllegalArgumentException("Username already in use");
            }
            existing.setUsername(incoming.getUsername());
        }

        if (incoming.getName() != null)
            existing.setName(incoming.getName());
        if (incoming.getLastName() != null)
            existing.setLastName(incoming.getLastName());
        if (incoming.getPhone() != null)
            existing.setPhone(incoming.getPhone());
        if (incoming.getBirthDate() != null)
            existing.setBirthDate(incoming.getBirthDate());
        if (incoming.getAddress() != null)
            existing.setAddress(incoming.getAddress());
        existing.setActive(incoming.getActive() != null ? incoming.getActive() : existing.isActive());

        if (incoming.getPassword() != null && !incoming.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(incoming.getPassword()));
        }

        if (incoming.getComunaId() != null) {
            Comuna comuna = comunaRepository.findById(incoming.getComunaId())
                    .orElseThrow(() -> new IllegalArgumentException("Comuna not found"));
            existing.setComuna(comuna);
        }
        if (incoming.getLaboratoryId() != null) {
            Laboratory lab = laboratoryRepository.findById(incoming.getLaboratoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Laboratory not found"));
            existing.setLaboratory(lab);
        }
        if (incoming.getRoleIds() != null && !incoming.getRoleIds().isEmpty()) {
            Set<Role> roles = new HashSet<>(roleRepository.findAllById(incoming.getRoleIds()));
            if (roles.size() != incoming.getRoleIds().size()) {
                throw new IllegalArgumentException("One or more roles not found");
            }
            existing.setRoles(roles);
        }

        existing.setUpdatedAt(LocalDateTime.now());
        return UserResponse.builder()
                .userId(existing.getUserId())
                .rut(existing.getRut())
                .username(existing.getUsername())
                .name(existing.getName())
                .lastName(existing.getLastName())
                .email(existing.getEmail())
                .phone(existing.getPhone())
                .birthDate(existing.getBirthDate())
                .address(existing.getAddress())
                .active(existing.isActive())
                .comunaId(existing.getComuna() != null ? existing.getComuna().getId() : null)
                .laboratoryId(existing.getLaboratory() != null ? existing.getLaboratory().getLabId() : null)
                .roleIds(existing.getRoles().stream().map(Role::getRoleId).collect(Collectors.toSet()))
                .createdAt(existing.getCreatedAt())
                .updatedAt(existing.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        userRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse loginUser(LoginRequest userRequest) {
        String email = userRequest.getEmail();
        String password = userRequest.getPassword();
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email or password is null");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Credentials are not valid");
        }
        return UserResponse.builder()
                .userId(user.getUserId())
                .rut(user.getRut())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .active(user.isActive())
                .comunaId(user.getComuna() != null ? user.getComuna().getId() : null)
                .laboratoryId(user.getLaboratory() != null ? user.getLaboratory().getLabId() : null)
                .roleIds(user.getRoles().stream().map(Role::getRoleId).collect(Collectors.toSet()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
