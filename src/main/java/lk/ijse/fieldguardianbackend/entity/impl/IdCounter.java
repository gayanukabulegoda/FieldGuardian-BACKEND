package lk.ijse.fieldguardianbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "id_counter")
public class IdCounter implements SuperEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 10, unique = true)
    private String prefix;
    @Column(name = "last_count", nullable = false, length = 10)
    private int lastCount;
}