package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * This is the repository layer which is responsible for managing the field data in the database
 */
@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
    @Query("SELECT f FROM Field f WHERE f.code = :id AND f.status <> :status")
    Optional<Field> findByIdAndStatusNot(String id, Status status);
    @Query("SELECT f FROM Field f WHERE f.status <> :status ORDER BY f.code DESC")
    List<Field> findAllByStatusNot(Status status);
    @Query("SELECT f.crops FROM Field f WHERE f.code = :fieldId AND f.status <> :status")
    List<Crop> findCropsByFieldId(String fieldId, Status status);
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Field f WHERE f.code = :id AND f.status = :status")
    boolean existsByIdAndStatus(String id, Status status);
}