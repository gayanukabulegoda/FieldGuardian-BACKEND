package lk.ijse.fieldguardianbackend.customObj.impl;

import lk.ijse.fieldguardianbackend.customObj.InternalServerResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InternalServerErrorResponse implements InternalServerResponse {
    private int errorCode;
    private String errorMessage;
}