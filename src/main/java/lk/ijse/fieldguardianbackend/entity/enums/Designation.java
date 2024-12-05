package lk.ijse.fieldguardianbackend.entity.enums;

import lk.ijse.fieldguardianbackend.exception.IllegalDesignationException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * This enum class represents the designation of an employee.
 * The designation of an employee can be one of the following:
 * 1. MANAGER - Represents a manager
 * 2. SENIOR_ASSISTANT_MANAGER - Represents a senior assistant manager
 * 3. ASSISTANT_MANAGER - Represents an assistant manager
 * 4. ADMIN_AND_HR_STAFF - Represents an administrative and HR staff
 * 5. OFFICE_ASSISTANT - Represents an office assistant
 * 6. SENIOR_AGRONOMIST - Represents a senior agronomist
 * 7. AGRONOMIST - Represents an agronomist
 * 8. SOIL_SCIENTIST - Represents a soil scientist
 * 9. SENIOR_TECHNICIAN - Represents a senior technician
 * 10. TECHNICIAN - Represents a technician
 * 11. SUPERVISORS - Represents a supervisor
 * 12. LABORS - Represents a labor
 */
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

    public static Designation fromString(String designation) {
        String formattedDesignation = designation.replace(" ", "_").toUpperCase();
        for (Designation d : Designation.values()) {
            if (d.name().equals(formattedDesignation)) return d;
        }
        throw new IllegalDesignationException("Invalid designation: " + designation);
    }
}