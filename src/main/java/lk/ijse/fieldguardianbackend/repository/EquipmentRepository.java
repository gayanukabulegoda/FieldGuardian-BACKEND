package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.enums.EquipmentStatus;
import lk.ijse.fieldguardianbackend.entity.impl.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * This is the repository layer which is responsible for managing the equipment data in the database
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, String> {
    @Query("SELECT e FROM Equipment e WHERE e.id = :id AND e.status <> :status")
    Optional<Equipment> findByIdAndStatusNot(String id, EquipmentStatus status);
    @Query("SELECT e FROM Equipment e WHERE e.status <> :status")
    List<Equipment> findAllByStatusNot(EquipmentStatus status);
    @Query("SELECT e FROM Equipment e WHERE e.status = :status")
    List<Equipment> findAllByStatus(EquipmentStatus status);
    @Query("SELECT e FROM Equipment e WHERE e.assignedField.code = :fieldCode AND" +
            " e.status = lk.ijse.fieldguardianbackend.entity.enums.EquipmentStatus.IN_USE")
    List<Equipment> findByFieldCode(String fieldCode);
}