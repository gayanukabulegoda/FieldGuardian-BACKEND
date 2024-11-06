package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CropSaveDTO implements SuperDTO, CropResponse {
    private String code;
    private String commonName;
    private String scientificName;
    private MultipartFile cropImage;
    private String category;
    private String season;
    private String fieldCode;
}