package lk.ijse.fieldguardianbackend.entity.impl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
import lk.ijse.fieldguardianbackend.entity.enums.Designation;
import lk.ijse.fieldguardianbackend.entity.enums.Gender;
import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate joinedDate;
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    @Column(nullable = false, length = 150)
    private String address;
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;
    @Column(name = "contact_no", nullable = false, unique = true, length = 15)
    private String contactNo;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 15)
    private String role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Vehicle> vehicles = new ArrayList<>();
    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<Field> fields = new ArrayList<>();
    @ManyToMany(mappedBy = "staff")
    @JsonIgnore
    private List<MonitoringLog> monitoringLogs = new ArrayList<>();
}