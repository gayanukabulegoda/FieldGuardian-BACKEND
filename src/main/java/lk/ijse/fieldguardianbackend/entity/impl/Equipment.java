package lk.ijse.fieldguardianbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
import lk.ijse.fieldguardianbackend.entity.enums.EquipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "equipment")
public class Equipment implements SuperEntity {
    @Id
    private String id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 20)
    private String type;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentStatus status;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_staff_id", referencedColumnName = "id")
    private Staff assignedStaff;
    @ManyToOne
    @JoinColumn(name = "assigned_field_id", referencedColumnName = "code")
    private Field assignedField;
}