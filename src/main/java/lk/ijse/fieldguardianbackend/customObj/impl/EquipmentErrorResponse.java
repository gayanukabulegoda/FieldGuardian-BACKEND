package lk.ijse.fieldguardianbackend.customObj.impl;

import lk.ijse.fieldguardianbackend.customObj.EquipmentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentErrorResponse implements EquipmentResponse {
    private int errorCode;
    private String errorMessage;
}