package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.jwtModels.JwtAuthResponse;
import lk.ijse.fieldguardianbackend.jwtModels.UserRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    JwtAuthResponse signIn(UserRequestDTO signIn);
    JwtAuthResponse signUp(UserRequestDTO signUp);
    JwtAuthResponse refreshToken(String accessToken);
    void verifyUserEmail(String option, String email);
}