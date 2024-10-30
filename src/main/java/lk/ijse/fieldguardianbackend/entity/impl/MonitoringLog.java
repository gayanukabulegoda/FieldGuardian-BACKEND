package lk.ijse.fieldguardianbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
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
@Table(name = "monitoring_log")
public class MonitoringLog implements SuperEntity {
    @Id
    private String code;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false, length = 150)
    private String details;
    @Column(name = "observed_image", columnDefinition = "LONGTEXT")
    private String observedImage;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "monitoring_log_field",
            joinColumns = @JoinColumn(name = "monitoring_log_id", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "code")
    )
    private List<Field> fields = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "monitoring_log_staff",
            joinColumns = @JoinColumn(name = "monitoring_log_id", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "staff_id", referencedColumnName = "id")
    )
    private List<Staff> staff = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "monitoring_log_crop",
            joinColumns = @JoinColumn(name = "monitoring_log_id", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "crop_id", referencedColumnName = "code")
    )
    private List<Crop> crops = new ArrayList<>();
}
