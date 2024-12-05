package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MonitoringLogSaveDTO implements SuperDTO, MonitoringLogResponse {
    private String code;
    @NotNull(message = "Date is mandatory")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotBlank(message = "Details is mandatory")
    @Size(min = 3, max = 400, message = "Details must be between 3 and 400 characters")
    private String details;
    private MultipartFile observedImage;
    @NotBlank(message = "Field code is mandatory")
    private String fieldCode;
}