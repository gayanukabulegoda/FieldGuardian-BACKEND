package lk.ijse.fieldguardianbackend.dto.impl;

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
    private String monitoringLogId;
    private List<String> staffIds = new ArrayList<>();
    private List<String> cropCodes = new ArrayList<>();
}