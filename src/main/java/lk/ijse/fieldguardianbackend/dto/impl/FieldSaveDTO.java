package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldSaveDTO implements SuperDTO, FieldResponse {
    private String code;
    private String name;
    private String location;
    private Double extentSize;
    private MultipartFile fieldImage1;
    private MultipartFile fieldImage2;
//    private List<String> cropCodes;
//    private List<String> equipmentIds;
//    private List<String> monitoringLogIds;
//    private List<String> staffIds;
}