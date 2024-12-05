package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lk.ijse.fieldguardianbackend.customObj.EquipmentResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO, EquipmentResponse {
    private String id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @NotBlank(message = "Type is mandatory")
    @Size(min = 3, max = 30, message = "Type must be between 3 and 30 characters")
    private String type;
    private String status;
    private String assignedStaffId;
    private String assignedFieldCode;
}