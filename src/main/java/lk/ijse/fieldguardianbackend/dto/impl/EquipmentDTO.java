package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentDTO implements SuperDTO {
    private String id;
    private String name;
    private String type;
    private String status;
    private String assignedStaffId;
    private String assignedFieldCode;
}
