package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.impl.*;
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
    List<CropResponseDTO> getFieldCrops(String fieldId);
}