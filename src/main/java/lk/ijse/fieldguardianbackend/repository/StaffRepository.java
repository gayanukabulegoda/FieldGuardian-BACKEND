package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.Field;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.entity.impl.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    @Query("SELECT s FROM Staff s WHERE s.id = :id AND s.status = lk.ijse.fieldguardianbackend.entity.enums.Status.ACTIVE")
    Optional<Staff> findActiveStaffById(String id);
    @Query("SELECT s FROM Staff s WHERE s.status = lk.ijse.fieldguardianbackend.entity.enums.Status.ACTIVE")
    List<Staff> findAllActiveStaff();
    @Query("SELECT s.vehicles FROM Staff s WHERE s.id = :id")
    List<Vehicle> findVehiclesByStaffId(String id);
    @Query("SELECT s.fields FROM Staff s WHERE s.id = :id")
    List<Field> findFieldsByStaffId(String id);
    boolean existsByContactNo(String contactNo);
    boolean existsByEmail(String email);
}