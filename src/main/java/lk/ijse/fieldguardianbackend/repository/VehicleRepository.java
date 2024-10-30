package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
