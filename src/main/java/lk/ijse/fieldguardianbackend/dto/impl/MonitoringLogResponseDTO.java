package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogResponseDTO implements SuperDTO, MonitoringLogResponse {
    private String code;
    private LocalDate date;
    private String details;
    private String observedImage;
    private String fieldCode;
    private int staffCount;
}