package lk.ijse.fieldguardianbackend.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
