package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MonitoringLogService {
    void saveMonitoringLog(MonitoringLogSaveDTO monitoringLogSaveDTO);
    void updateMonitoringLog(String id, MonitoringLogSaveDTO monitoringLogSaveDTO);
    void updateMonitoringLogStaffAndCrops(UpdateMonitoringLogStaffAndCropsDTO updateDTO);
    MonitoringLogResponse getSelectedMonitoringLog(String id);
    List<MonitoringLogResponseDTO> getAllMonitoringLogs();
    List<CropResponseDTO> getCropsByMonitoringLogId(String monitoringLogId);
    List<StaffDTO> getStaffByMonitoringLogId(String monitoringLogId);
}