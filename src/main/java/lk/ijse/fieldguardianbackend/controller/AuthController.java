package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.OtpResponse;
import lk.ijse.fieldguardianbackend.jwtModels.JwtAuthResponse;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import lk.ijse.fieldguardianbackend.service.AuthService;
import lk.ijse.fieldguardianbackend.service.JwtService;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Endpoint for user registration.
     *
     * @param userRequestDTO the user registration request data
     * @return ResponseEntity containing the JWT authentication response
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signUp(@RequestBody UserRequestDTO userRequestDTO) {
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(userRequestDTO));
    }
    /**
     * Endpoint for user login.
     *
     * @param userRequestDTO the user login request data
     * @return ResponseEntity containing the JWT authentication response
     */
    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JwtAuthResponse> signIn(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(userRequestDTO));
    }
    /**
     * Endpoint for validating a user token.
     * @param token the JWT token
     * @return ResponseEntity with the validation status
     */
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateUser(@RequestHeader("Authorization") String token) {
        jwtService.extractUsername(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
    /**
     * Endpoint for requesting an OTP.
     *
     * @param option the option to verify the user
     * @param email the email of the user
     * @return ResponseEntity with the OTP response and OK status
     */
    @PostMapping("/otp")
    public ResponseEntity<OtpResponse> requestOtp(
            @RequestParam("option") String option, @RequestParam("email") String email)  {
        authService.verifyUserEmail(option, email);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}