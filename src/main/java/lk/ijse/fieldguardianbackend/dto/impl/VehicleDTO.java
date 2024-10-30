package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleDTO implements SuperDTO {
    private String code;
    private String licensePlateNumber;
    private String category;
    private String fuelType;
    private String status;
    private String remark;
    private String driverId;
}
