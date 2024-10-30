package lk.ijse.fieldguardianbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "crop")
public class Crop implements SuperEntity {
    @Id
    private String code;
    @Column(name = "crop_name", nullable = false, length = 50)
    private String commonName;
    @Column(name = "scientific_name", nullable = false, length = 50)
    private String scientificName;
    @Column(name = "crop_image", columnDefinition = "LONGTEXT")
    private String cropImage;
    @Column(nullable = false, length = 20)
    private String category;
    @Column(nullable = false, length = 10)
    private String season;
    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false, referencedColumnName = "code")
    private Field field;
    @ManyToMany(mappedBy = "crops")
    private List<MonitoringLog> monitoringLogs = new ArrayList<>();
}


