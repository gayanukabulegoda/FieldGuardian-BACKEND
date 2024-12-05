package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateEquipmentStaffDTO implements SuperDTO {
    @NotBlank(message = "Equipment Id is mandatory")
    private String equipmentId;
    @NotBlank(message = "Staff Id is mandatory")
    private String staffId;
}