package lk.ijse.fieldguardianbackend.entity.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.enums.VehicleStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    private String code;
    @Column(name = "license_plate_number", nullable = false, unique = true, length = 10)
    private String licensePlateNumber;
    @Column(nullable = false, length = 30)
    private String category;
    @Column(name = "fuel_type", nullable = false, length = 10)
    private String fuelType;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleStatus status;
    @Column(nullable = false, length = 150)
    private String remark;
    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    @JsonManagedReference
    private Staff driver;
}

