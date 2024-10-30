package lk.ijse.fieldguardianbackend.entity.enums;

import lk.ijse.fieldguardianbackend.exception.IllegalGenderException;

public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender fromString(String gender) {
        for (Gender g : Gender.values()) if (g.name().equalsIgnoreCase(gender)) return g;
        throw new IllegalGenderException("Invalid gender: " + gender);
    }
}