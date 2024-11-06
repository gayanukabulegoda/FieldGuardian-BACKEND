package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String designation;
    private String gender;
    private Date joinedDate;
    private Date dateOfBirth;
    private String address;
    private String postalCode;
    private String contactNo;
    private String email;
    private String role;
}