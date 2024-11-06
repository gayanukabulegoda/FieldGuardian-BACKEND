package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldResponseDTO implements SuperDTO, FieldResponse {
    private String code;
    private String name;
    private String location;
    private Double extentSize;
    private String fieldImage1;
    private String fieldImage2;
}