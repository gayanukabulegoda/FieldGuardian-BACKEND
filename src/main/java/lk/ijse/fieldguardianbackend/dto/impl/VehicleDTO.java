package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleDTO implements SuperDTO, VehicleResponse {
    private String code;
    @NotBlank(message = "LicensePlateNumber is mandatory")
    @Size(min = 7, max = 10, message = "LicensePlateNumber must be between 7 and 10 characters")
    private String licensePlateNumber;
    @NotBlank(message = "Category is mandatory")
    @Size(min = 3, max = 30, message = "Category must be between 3 and 30 characters")
    private String category;
    @NotBlank(message = "FuelType is mandatory")
    @Size(min = 3, max = 10, message = "FuelType must be between 3 and 10 characters")
    private String fuelType;
    private String status;
    @NotBlank(message = "Remark is mandatory")
    @Size(min = 3, max = 400, message = "Remark must be between 3 and 400 characters")
    private String remark;
    private String driverId;
}