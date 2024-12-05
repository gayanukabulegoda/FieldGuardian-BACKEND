package lk.ijse.fieldguardianbackend.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * This enum class represents the prefixes of the IDs of the entities.
 * The prefixes of the IDs of the entities can be one of the following:
 * 1. CRP - The prefix of the ID of a crop
 * 2. EQP - The prefix of the ID of an equipment
 * 3. FLD - The prefix of the ID of a field
 * 4. MNL - The prefix of the ID of a monitoring log
 * 5. STF - The prefix of the ID of a staff member
 * 6. USR - The prefix of the ID of a user
 * 7. VHL - The prefix of the ID of a vehicle
 */
@Getter
@RequiredArgsConstructor
public enum IdPrefix {
    CROP("CRP"),
    EQUIPMENT("EQP"),
    FIELD("FLD"),
    MONITORING_LOG("MNL"),
    STAFF("STF"),
    USER("USR"),
    VEHICLE("VHL");
    private final String prefix;
}