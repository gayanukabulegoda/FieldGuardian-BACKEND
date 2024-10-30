package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.impl.FieldDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FieldService {
    void saveField(FieldDTO fieldDTO);
    void updateField(String id, FieldDTO fieldDTO);
    void deleteField(String id);
    FieldResponse getFieldById(String id);
    List<FieldDTO> getAllFields();
}
