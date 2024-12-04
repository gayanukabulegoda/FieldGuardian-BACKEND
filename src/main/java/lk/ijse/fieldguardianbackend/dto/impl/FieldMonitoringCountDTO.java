package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldMonitoringCountDTO implements SuperDTO {
    private String fieldName;
    private long monitoringCount;
}