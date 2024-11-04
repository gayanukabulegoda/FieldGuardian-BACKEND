package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
    @Query("SELECT f FROM Field f WHERE f.code = :id AND f.status <> :status")
    Optional<Field> findByIdAndStatusNot(String id, Status status);
    @Query("SELECT f FROM Field f WHERE f.status <> :status")
    List<Field> findAllByStatusNot(Status status);
    @Query("SELECT f.crops FROM Field f WHERE f.code = :fieldId AND f.status <> :status")
    List<Crop> findCropsByFieldId(String fieldId, Status status);
}