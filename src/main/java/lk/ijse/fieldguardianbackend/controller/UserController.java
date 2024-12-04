package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.UserResponse;
import lk.ijse.fieldguardianbackend.dto.impl.UserResponseDTO;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import lk.ijse.fieldguardianbackend.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
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
        userRequestDTO.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userService.updateUser(userRequestDTO);
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
        userService.deleteUser(email);
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
        return ResponseEntity.status(HttpStatus.OK).body(userService.getSelectedUser(email));
    }
    /**
     * Endpoint for retrieving all users.
     *
     * @return ResponseEntity with the list of all users and OK status
     */
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
}