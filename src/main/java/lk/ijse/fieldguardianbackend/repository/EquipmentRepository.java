package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
}
