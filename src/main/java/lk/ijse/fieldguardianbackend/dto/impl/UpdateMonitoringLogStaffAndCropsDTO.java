package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateMonitoringLogStaffAndCropsDTO implements SuperDTO {
    @NotBlank(message = "Monitoring Log Id is mandatory")
    private String monitoringLogId;
    @NotEmpty(message = "Staff Ids can not be empty")
    private List<String> staffIds = new ArrayList<>();
    @NotEmpty(message = "Crop Codes can not be empty")
    private List<String> cropCodes = new ArrayList<>();
}