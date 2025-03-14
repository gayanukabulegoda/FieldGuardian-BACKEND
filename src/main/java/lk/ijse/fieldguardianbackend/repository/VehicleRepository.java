package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.enums.VehicleStatus;
import lk.ijse.fieldguardianbackend.entity.impl.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * This is the repository layer which is responsible for managing the vehicle data in the database
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Vehicle v WHERE v.licensePlateNumber = :licensePlateNumber")
    boolean findByLicensePlateNumber(String licensePlateNumber);
    @Query("SELECT v FROM Vehicle v WHERE v.code = :id AND v.status <> :status")
    Optional<Vehicle> findByIdAndStatusNot(String id, VehicleStatus status);
    @Query("SELECT v FROM Vehicle v WHERE v.status <> :status ORDER BY v.code DESC")
    List<Vehicle> findAllByStatusNot(VehicleStatus status);
    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.status <> :status")
    long countAllActiveVehicles(VehicleStatus status);
    @Query("SELECT v FROM Vehicle v WHERE " +
            "(:licencePlateNo IS NULL OR LOWER(v.licensePlateNumber) LIKE LOWER(CONCAT('%', :licencePlateNo, '%'))) " +
            "AND (:category IS NULL OR LOWER(v.category) LIKE LOWER(CONCAT('%', :category, '%'))) " +
            "AND (:status IS NULL OR v.status = :status)")
    List<Vehicle> findAllByFilters(String licencePlateNo, String category, VehicleStatus status);
}