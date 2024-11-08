package lk.ijse.fieldguardianbackend.customObj.impl;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogErrorResponse implements MonitoringLogResponse {
    private int errorCode;
    private String errorMessage;
}