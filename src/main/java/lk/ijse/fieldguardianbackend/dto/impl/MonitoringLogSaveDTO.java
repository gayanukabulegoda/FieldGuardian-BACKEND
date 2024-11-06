package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogSaveDTO implements SuperDTO, MonitoringLogResponse {
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String details;
    private MultipartFile observedImage;
    private String fieldCode;
}