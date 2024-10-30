package lk.ijse.fieldguardianbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
import lk.ijse.fieldguardianbackend.entity.enums.Designation;
import lk.ijse.fieldguardianbackend.entity.enums.Gender;
import lk.ijse.fieldguardianbackend.entity.enums.StaffStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "staff")
public class Staff implements SuperEntity {
    @Id
    private String id;
    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Designation designation;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    @Column(name = "joined_date", nullable = false)
    private Date joinedDate;
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;
    @Column(name = "contact_no", nullable = false, length = 10)
    private String contactNo;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 15)
    private String role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StaffStatus status;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
    @ManyToMany(mappedBy = "staff")
    private List<Field> fields = new ArrayList<>();
    @ManyToMany(mappedBy = "staff")
    private List<MonitoringLog> monitoringLogs = new ArrayList<>();
}
