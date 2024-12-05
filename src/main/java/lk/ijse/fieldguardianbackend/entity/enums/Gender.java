package lk.ijse.fieldguardianbackend.entity.enums;

import lk.ijse.fieldguardianbackend.exception.IllegalGenderException;
/**
 * This enum class represents the gender of an individual.
 * The gender of an individual can be one of the following:
 * 1. MALE - Represents a male gender
 * 2. FEMALE - Represents a female gender
 * 3. OTHER - Represents a non-binary or other gender
 */
public enum Gender {
    MALE,
    FEMALE,
    OTHER;

    public static Gender fromString(String gender) {
        for (Gender g : Gender.values()) if (g.name().equalsIgnoreCase(gender)) return g;
        throw new IllegalGenderException("Invalid gender: " + gender);
    }
}