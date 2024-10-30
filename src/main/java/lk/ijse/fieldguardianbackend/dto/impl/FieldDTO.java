package lk.ijse.fieldguardianbackend.dto.impl;

import lk.ijse.fieldguardianbackend.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldDTO implements SuperDTO {
    private String code;
    private String name;
    private String location;
    private Double extentSize;
    private String fieldImage1;
    private String fieldImage2;
    private List<String> cropCodes;
    private List<String> monitoringLogIds;
    private List<String> staffIds;
}
