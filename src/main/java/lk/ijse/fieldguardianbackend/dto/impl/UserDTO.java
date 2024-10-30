package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements SuperDTO {
    private String email;
    private String password;
    private String role;
}
