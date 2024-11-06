package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CropRepository extends JpaRepository<Crop, String> {
    @Query("SELECT c FROM Crop c WHERE c.code = :id AND c.status = :status")
    Optional<Crop> findByIdAndStatus(String id, Status status);
    @Query("SELECT c FROM Crop c WHERE c.status = :status")
    List<Crop> findAllByStatus(Status status);
}