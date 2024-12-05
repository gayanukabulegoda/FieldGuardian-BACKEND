package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Common Name is mandatory")
    @Size(min = 3, max = 50, message = "Common Name must be between 3 and 50 characters")
    private String commonName;
    @NotBlank(message = "Scientific Name is mandatory")
    @Size(min = 3, max = 50, message = "Scientific Name must be between 3 and 50 characters")
    private String scientificName;
    private MultipartFile cropImage;
    @NotBlank(message = "Category is mandatory")
    @Size(min = 3, max = 20, message = "Category must be between 3 and 20 characters")
    private String category;
    @NotBlank(message = "Season is mandatory")
    @Size(min = 3, max = 30, message = "Season must be between 3 and 30 characters")
    private String season;
    @NotBlank(message = "FieldCode is mandatory")
    private String fieldCode;
}