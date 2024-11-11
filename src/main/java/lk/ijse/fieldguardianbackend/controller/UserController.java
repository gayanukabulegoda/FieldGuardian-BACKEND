package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.UserResponse;
import lk.ijse.fieldguardianbackend.dto.impl.UserResponseDTO;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import lk.ijse.fieldguardianbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * UserController class (REST Controller)
 * This class handles the user related requests
 * such as updating, deleting, getting all users and getting a specific user
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    /**
     * Endpoint for updating a user.
     *
     * @param userRequestDTO the user data to update
     * @return ResponseEntity with no content status
     */
    @PatchMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateUser(@RequestBody UserRequestDTO userRequestDTO) {
        log.info("Request to update user: {}", userRequestDTO);
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userService.updateUser(userRequestDTO);
        log.info("User updated successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * Endpoint for deleting a user by email.
     *
     * @param email the email of the user to delete
     * @return ResponseEntity with no content status
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestParam("email") String email) {
        log.info("Request to delete user with email: {}", email);
        userService.deleteUser(email);
        log.info("User deleted successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * Endpoint for retrieving a user by email.
     *
     * @param email the email of the user to retrieve
     * @return ResponseEntity with the user data and OK status
     */
    @GetMapping
    public ResponseEntity<UserResponse> getUser(@RequestParam("email") String email) {
        log.info("Request to get user with email: {}", email);
        UserResponse userResponse = userService.getSelectedUser(email);
        log.info("User retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
    /**
     * Endpoint for retrieving all users.
     *
     * @return ResponseEntity with the list of all users and OK status
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Request to get all users");
        List<UserResponseDTO> users = userService.getAllUsers();
        log.info("All users retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}