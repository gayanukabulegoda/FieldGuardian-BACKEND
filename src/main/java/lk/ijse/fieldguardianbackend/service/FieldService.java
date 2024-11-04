package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropDTO;
import lk.ijse.fieldguardianbackend.dto.impl.FieldResponseDTO;
import lk.ijse.fieldguardianbackend.dto.impl.FieldSaveDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldService {
    void saveField(FieldSaveDTO fieldSaveDTO);
    void updateField(String id, FieldSaveDTO fieldSaveDTO);
    void deleteField(String id);
    FieldResponse getFieldById(String id);
    List<FieldResponseDTO> getAllFields();
    List<StaffDTO> getFieldStaff(String fieldId);
    void updateFieldStaff(String fieldId, List<String> staffIds);
    List<CropDTO> getFieldCrops(String fieldId);
}