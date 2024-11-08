package lk.ijse.fieldguardianbackend.dto.impl;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "Name is mandatory")
    @Size(min = 3, max = 40, message = "Name must be between 3 and 40 characters")
    private String name;
    @NotBlank(message = "Location is mandatory")
    @Size(min = 3, max = 100, message = "Location must be between 3 and 100 characters")
    private String location;
    @NotNull(message = "Extent size is mandatory")
    @Positive(message = "Extent size must be a positive number")
    private Double extentSize;
    private MultipartFile fieldImage1;
    private MultipartFile fieldImage2;
}