package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.*;
import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffDTO implements SuperDTO, StaffResponse {
    private String id;
    @NotBlank(message = "First Name is mandatory")
    @Size(min = 3, max = 20, message = "First Name must be between 3 and 20 characters")
    private String firstName;
    @NotBlank(message = "Last Name is mandatory")
    @Size(min = 3, max = 20, message = "Last Name must be between 3 and 20 characters")
    private String lastName;
    @NotBlank(message = "Designation is mandatory")
    private String designation;
    @NotBlank(message = "Gender is mandatory")
    private String gender;
    @NotNull(message = "Joined Date is mandatory")
    private LocalDate joinedDate;
    @NotNull(message = "Date of Birth is mandatory")
    private LocalDate dateOfBirth;
    @NotBlank(message = "Address is mandatory")
    @Size(min = 3, max = 150, message = "Address must be between 3 and 150 characters")
    private String address;
    @NotBlank(message = "Postal Code is mandatory")
    @Size(min = 5, max = 10, message = "Postal Code must be between 5 and 10 characters")
    private String postalCode;
    @NotBlank(message = "Contact No is mandatory")
    @Pattern(regexp = "\\+?[\\d\\s-]{10,}", message = "Contact number must be 10 digits")
    private String contactNo;
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    private String email;
    private String role;
}