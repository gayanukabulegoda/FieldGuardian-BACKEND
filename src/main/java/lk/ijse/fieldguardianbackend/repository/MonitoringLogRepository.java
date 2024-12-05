package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.dto.impl.FieldMonitoringCountDTO;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.entity.impl.MonitoringLog;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
/**
 * This is the repository layer which is responsible for managing the monitoring log data in the database
 */
@Repository
public interface MonitoringLogRepository extends JpaRepository<MonitoringLog, String> {
    @Query("SELECT m.crops FROM MonitoringLog m WHERE m.code = :monitoringLogId")
    List<Crop> findCropsByMonitoringLogId(String monitoringLogId);
    @Query("SELECT m.staff FROM MonitoringLog m WHERE m.code = :monitoringLogId")
    List<Staff> findStaffByMonitoringLogId(String monitoringLogId);
    @Query("SELECT COUNT(s) FROM MonitoringLog m JOIN m.staff s WHERE m.code = :monitoringLogId")
    int getStaffCountByMonitoringLogId(String monitoringLogId);
    @Query("SELECT new lk.ijse.fieldguardianbackend.dto.impl.FieldMonitoringCountDTO(f.name, COUNT(m)) " +
            "FROM MonitoringLog m JOIN m.field f " +
            "GROUP BY f.name " +
            "ORDER BY COUNT(m) DESC")
    List<FieldMonitoringCountDTO> findTopFiveFieldsWithHighestMonitoringLogs();
    @Query("SELECT m FROM MonitoringLog m WHERE " +
            "(:name IS NULL OR LOWER(m.field.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:startOfDay IS NULL OR m.date >= :startOfDay) " +
            "AND (:endOfDay IS NULL OR m.date <= :endOfDay)")
    List<MonitoringLog> findAllByFilters(String name, LocalDate startOfDay, LocalDate endOfDay);
}