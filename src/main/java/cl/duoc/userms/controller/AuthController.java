package cl.duoc.userms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.userms.dto.LoginRequest;
import cl.duoc.userms.dto.UserResponse;
import cl.duoc.userms.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Logging in user: {}", loginRequest);
        UserResponse user = userService.loginUser(loginRequest);
        if (user == null) {
            logger.error("User not found: {}", loginRequest);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        logger.info("User logged in successfully: {}", user);
        return ResponseEntity.ok(user);
    }
}
