package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.UserResponse;
import lk.ijse.fieldguardianbackend.dto.impl.UserResponseDTO;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {
    void updateUser(UserRequestDTO userRequestDTO);
    void deleteUser(String email);
    UserResponse getSelectedUser(String email);
    List<UserResponseDTO> getAllUsers();
    UserDetailsService userDetailsService();
}