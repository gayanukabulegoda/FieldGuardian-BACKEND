package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface VehicleService {
    void saveVehicle(VehicleDTO vehicleDTO);
    void updateVehicle(String id, VehicleDTO vehicleDTO);
    void updateVehicleDriver(String vehicleId, String driverId);
    void deleteVehicle(String id);
    VehicleResponse getSelectedVehicle(String id);
    List<VehicleDTO> getAllVehicles();
}