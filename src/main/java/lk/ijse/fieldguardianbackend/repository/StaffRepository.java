package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    @Query("SELECT s FROM Staff s WHERE s.id = :id AND s.status = lk.ijse.fieldguardianbackend.entity.enums.StaffStatus.ACTIVE")
    Optional<Staff> findActiveStaffById(String id);
    @Query("SELECT s FROM Staff s WHERE s.status = lk.ijse.fieldguardianbackend.entity.enums.StaffStatus.ACTIVE")
    List<Staff> findAllActiveStaff();
}
