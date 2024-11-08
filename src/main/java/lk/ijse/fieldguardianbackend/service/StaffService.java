package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffFieldDTO;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface StaffService {
    void saveStaff(StaffDTO staffDTO);
    void updateStaff(String id, StaffDTO staffDTO);
    void deleteStaff(String id);
    StaffResponse getSelectedStaff(String id);
    List<StaffDTO> getAllStaffs();
    List<VehicleDTO> getStaffVehicles(String staffId);
    List<StaffFieldDTO> getStaffFields(String staffId);
    List<StaffDTO> getStaffWithoutEquipment();
}