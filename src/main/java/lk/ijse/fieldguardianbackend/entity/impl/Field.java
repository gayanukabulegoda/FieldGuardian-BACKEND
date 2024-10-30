package lk.ijse.fieldguardianbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "field")
public class Field implements SuperEntity {
    @Id
    private String code;
    @Column(nullable = false, length = 40)
    private String name;
    @Column(nullable = false, length = 100)
    private Point location;
    @Column(name = "extent_size", nullable = false, length = 10)
    private Double extentSize;
    @Column(name = "field_image1", columnDefinition = "LONGTEXT")
    private String fieldImage1;
    @Column(name = "field_image2", columnDefinition = "LONGTEXT")
    private String fieldImage2;
    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Crop> crops = new ArrayList<>();
    @OneToMany(mappedBy = "assignedField", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Equipment> equipments = new ArrayList<>();
    @ManyToMany(mappedBy = "fields")
    private List<MonitoringLog> monitoringLogs = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "field_staff",
            joinColumns = @JoinColumn(name = "field_id", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id")
    )
    private List<Staff> staff = new ArrayList<>();
}
