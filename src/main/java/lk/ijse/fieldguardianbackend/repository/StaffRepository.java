package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
}
