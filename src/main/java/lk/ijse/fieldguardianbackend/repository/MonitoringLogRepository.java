package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.MonitoringLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitoringLogRepository extends JpaRepository<MonitoringLog, String> {
}
