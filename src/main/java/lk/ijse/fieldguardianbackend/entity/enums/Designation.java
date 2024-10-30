package lk.ijse.fieldguardianbackend.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Designation {
    MANAGER("MANAGER"),
    SENIOR_ASSISTANT_MANAGER("MANAGER"),
    ASSISTANT_MANAGER("MANAGER"),
    ADMIN_AND_HR_STAFF("ADMINISTRATIVE"),
    OFFICE_ASSISTANT("OTHER"),
    SENIOR_AGRONOMIST("SCIENTIST"),
    AGRONOMIST("SCIENTIST"),
    SOIL_SCIENTIST("SCIENTIST"),
    SENIOR_TECHNICIAN("OTHER"),
    TECHNICIAN("OTHER"),
    SUPERVISORS("OTHER"),
    LABORS("OTHER");
    private final String role;
}