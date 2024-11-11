package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.entity.impl.User;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.JwtAuthenticationException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.jwtModels.JwtAuthResponse;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.repository.UserRepository;
import lk.ijse.fieldguardianbackend.service.AuthService;
import lk.ijse.fieldguardianbackend.service.JwtService;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * This class was created to handle authentication related services
 * AuthService Implementation
 * @author - Gayanuka Bulegoda
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final JwtService jwtService;
    private final Mapping mapping;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthResponse signIn(UserRequestDTO signIn) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid credentials", e);
        }
        var userByEmail = userRepository.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            var generatedToken = jwtService.generateToken(userByEmail);
            return JwtAuthResponse.builder().token(generatedToken).build();
        } catch (Exception e) {
            throw new JwtAuthenticationException("Failed to generate JWT token", e);
        }
    }

    @Override
    public JwtAuthResponse signUp(UserRequestDTO signUpUser) {
        Staff staff = staffRepository.findByEmailAndStatusNot(signUpUser.getEmail(), Status.REMOVED)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        userRepository.findByEmail(signUpUser.getEmail())
                .ifPresent(user -> {
                    throw new DataPersistFailedException("Email already exists", 2);
                });
        String role = staff.getDesignation().getRole();
        if (role == null || role.equals("OTHER") ||
                !(role.equals("MANAGER") || role.equals("ADMINISTRATIVE") || role.equals("SCIENTIST")))
            throw new DataPersistFailedException("Role not found or not authorized", 3);
        User user = mapping.convertToEntity(signUpUser, User.class);
        user.setRole(role);
        try {
            User savedUser = userRepository.save(user);
            var generatedToken = jwtService.generateToken(savedUser);
            return JwtAuthResponse.builder().token(generatedToken).build();
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save User", 0, e);
        }
    }

    @Override
    public JwtAuthResponse refreshToken(String accessToken) {
        var email = jwtService.extractUsername(accessToken);
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var refreshToken = jwtService.refreshToken(user);
        return JwtAuthResponse.builder().token(refreshToken).build();
    }
}