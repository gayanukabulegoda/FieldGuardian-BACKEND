package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CropResponseDTO implements SuperDTO, CropResponse {
    private String code;
    private String commonName;
    private String scientificName;
    private String cropImage;
    private String category;
    private String season;
    private String fieldCode;
}