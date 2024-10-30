package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.impl.MonitoringLogDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MonitoringLogService {
    void saveMonitoringLog(MonitoringLogDTO monitoringLogDTO);
    void updateMonitoringLog(String id, MonitoringLogDTO monitoringLogDTO);
    void deleteMonitoringLog(String id);
    MonitoringLogResponse getSelectedMonitoringLog(String id);
    List<MonitoringLogDTO> getAllMonitoringLogs();
}
