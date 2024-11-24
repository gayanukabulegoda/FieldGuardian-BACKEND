package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.UserResponse;
import lk.ijse.fieldguardianbackend.dto.impl.UserResponseDTO;
import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.entity.impl.User;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.exception.UserNotFoundException;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.repository.UserRepository;
import lk.ijse.fieldguardianbackend.service.UserService;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
 * This class was created to implement the business logic of the User
 * Service Implementation
 * @author Gayanuka Bulegoda
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final Mapping mapping;

    @Override
    @Transactional
    public void updateUser(UserRequestDTO userRequestDTO) {
        User user = userRepository.findByEmail(userRequestDTO.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setPassword(userRequestDTO.getPassword());
    }

    @Override
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }

    @Override
    public UserResponse getSelectedUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserResponseDTO userResponseDTO = mapping.convertToDTO(user, UserResponseDTO.class);
        Staff staff = staffRepository.findByEmailAndStatusNot(user.getEmail(), Status.REMOVED)
                .orElseThrow(() -> new StaffNotFoundException("User not found in staff"));
        userResponseDTO.setName(staff.getFirstName() + " " + staff.getLastName());
        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) throw new UserNotFoundException("No users found");
        List<UserResponseDTO> userResponseDTOS = mapping.convertToDTOList(users, UserResponseDTO.class);
        for (UserResponseDTO userResponseDTO : userResponseDTOS) {
            Staff staff = staffRepository.findByEmailAndStatusNot(userResponseDTO.getEmail(), Status.REMOVED)
                    .orElseThrow(() -> new StaffNotFoundException("User not found in staff"));
            userResponseDTO.setName(staff.getFirstName() + " " + staff.getLastName());
        }
        return userResponseDTOS;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
