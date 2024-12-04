package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.entity.impl.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
/**
 * This is the repository layer which is responsible for managing the staff data in the database
 */
@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    @Query("SELECT s FROM Staff s WHERE s.id = :id AND s.status = :status")
    Optional<Staff> findActiveStaffById(String id, Status status);
    @Query("SELECT s FROM Staff s WHERE s.status = :status ORDER BY s.id DESC")
    List<Staff> findAllActiveStaff(Status status);
    @Query("SELECT s.vehicles FROM Staff s WHERE s.id = :id")
    List<Vehicle> findVehiclesByStaffId(String id);
    @Query("SELECT s.fields FROM Staff s WHERE s.id = :id")
    List<Field> findFieldsByStaffId(String id);
    boolean existsByContactNo(String contactNo);
    boolean existsByEmail(String email);
    @Query("SELECT s FROM Staff s WHERE s.status = :status AND s.id " +
            "NOT IN (SELECT e.assignedStaff.id FROM Equipment e WHERE e.assignedStaff IS NOT NULL)")
    List<Staff> findAllActiveStaffWithoutEquipment(Status status);
    @Query("SELECT s FROM Staff s WHERE s.email = :email AND s.status <> :status")
    Optional<Staff> findByEmailAndStatusNot(String email, Status status);
    @Query("SELECT COUNT(s) FROM Staff s WHERE s.status <> :status")
    long countAllActiveStaff(Status status);
}