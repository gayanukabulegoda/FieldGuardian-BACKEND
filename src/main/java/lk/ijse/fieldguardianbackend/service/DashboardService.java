package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.dto.impl.FieldMonitoringCountDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface DashboardService {
    long getTotalUsers();
    long getTotalActiveStaff();
    long getTotalActiveCrops();
    long getTotalActiveVehicles();
    long getTotalActiveEquipment();
    List<FieldMonitoringCountDTO> getTopFiveMonitoredFields();
}