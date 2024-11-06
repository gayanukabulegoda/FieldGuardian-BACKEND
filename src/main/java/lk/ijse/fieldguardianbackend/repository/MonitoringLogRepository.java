package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.entity.impl.MonitoringLog;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitoringLogRepository extends JpaRepository<MonitoringLog, String> {
    @Query("SELECT m.crops FROM MonitoringLog m WHERE m.code = :monitoringLogId")
    List<Crop> findCropsByMonitoringLogId(String monitoringLogId);
    @Query("SELECT m.staff FROM MonitoringLog m WHERE m.code = :monitoringLogId")
    List<Staff> findStaffByMonitoringLogId(String monitoringLogId);
}