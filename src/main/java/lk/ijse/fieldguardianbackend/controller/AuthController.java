package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.jwtModels.JwtAuthResponse;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import lk.ijse.fieldguardianbackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
/**
 * AuthController class (REST Controller)
 * This class handles the authentication related requests.
 * It provides endpoints for user sign-up, sign-in, and token refresh.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    /**
     * Endpoint for user registration.
     *
     * @param userRequestDTO the user registration request data
     * @return ResponseEntity containing the JWT authentication response
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("User sign up request received for email: {}", userRequestDTO.getEmail());
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        JwtAuthResponse response = authService.signUp(userRequestDTO);
        log.info("User registered successfully with email: {}", userRequestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    /**
     * Endpoint for user login.
     *
     * @param userRequestDTO the user login request data
     * @return ResponseEntity containing the JWT authentication response
     */
    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("User sign in request received for email: {}", userRequestDTO.getEmail());
        JwtAuthResponse response = authService.signIn(userRequestDTO);
        log.info("User signed in successfully with email: {}", userRequestDTO.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * Endpoint for refreshing the JWT token.
     *
     * @param refreshToken the refresh token
     * @return ResponseEntity containing the new JWT authentication response
     */
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthResponse> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        log.info("Refresh token request received");
        JwtAuthResponse response = authService.refreshToken(refreshToken);
        log.info("Token refreshed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}