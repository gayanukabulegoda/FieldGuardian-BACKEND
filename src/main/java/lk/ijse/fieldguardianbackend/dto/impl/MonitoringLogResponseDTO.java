package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogResponseDTO implements SuperDTO, MonitoringLogResponse {
    private String code;
    private Date date;
    private String details;
    private String observedImage;
    private String fieldCode;
}