package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.dto.impl.FieldMonitoringCountDTO;
import lk.ijse.fieldguardianbackend.entity.enums.EquipmentStatus;
import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.enums.VehicleStatus;
import lk.ijse.fieldguardianbackend.repository.*;
import lk.ijse.fieldguardianbackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * This class was created for the business logic of Dashboard
 * service implementation
 * @author - Gayanuka Bulegoda
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;
    private final CropRepository cropRepository;
    private final VehicleRepository vehicleRepository;
    private final EquipmentRepository equipmentRepository;
    private final MonitoringLogRepository monitoringLogRepository;

    @Override
    public long getTotalUsers() {
        return userRepository.countAllUsers();
    }
    @Override
    public long getTotalActiveStaff() {
        return staffRepository.countAllActiveStaff(Status.REMOVED);
    }
    @Override
    public long getTotalActiveCrops() {
        return cropRepository.countAllActiveCrops(Status.REMOVED);
    }
    @Override
    public long getTotalActiveVehicles() {
        return vehicleRepository.countAllActiveVehicles(VehicleStatus.REMOVED);
    }
    @Override
    public long getTotalActiveEquipment() {
        return equipmentRepository.countAllActiveEquipment(EquipmentStatus.OUT_OF_SERVICE);
    }
    @Override
    public List<FieldMonitoringCountDTO> getTopFiveMonitoredFields() {
        return monitoringLogRepository.findTopFiveFieldsWithHighestMonitoringLogs();
    }
}