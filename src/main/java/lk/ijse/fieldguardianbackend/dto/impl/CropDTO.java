package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CropDTO implements SuperDTO, CropResponse {
    private String code;
    private String commonName;
    private String scientificName;
    private String cropImage;
    private String category;
    private String season;
    private String fieldCode;
    private List<String> monitoringLogIds;
}
