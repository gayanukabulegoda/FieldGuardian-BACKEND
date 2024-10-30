package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StaffService {
    void saveStaff(StaffDTO staffDTO);
    void updateStaff(String id, StaffDTO staffDTO);
    void deleteStaff(String id);
    StaffResponse getSelectedStaff(String id);
    List<StaffDTO> getAllStaffs();
}
