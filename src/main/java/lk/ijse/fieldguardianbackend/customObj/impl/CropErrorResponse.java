package lk.ijse.fieldguardianbackend.customObj.impl;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CropErrorResponse implements CropResponse {
    private int errorCode;
    private String errorMessage;
}