package lk.ijse.fieldguardianbackend.customObj.impl;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffErrorResponse implements StaffResponse {
    private int errorCode;
    private String errorMessage;
}