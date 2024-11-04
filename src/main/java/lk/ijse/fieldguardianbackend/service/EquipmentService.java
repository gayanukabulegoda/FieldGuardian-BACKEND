package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.EquipmentResponse;
import lk.ijse.fieldguardianbackend.dto.impl.EquipmentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {
    void saveEquipment(EquipmentDTO equipmentDTO);
    void updateEquipment(String id, EquipmentDTO equipmentDTO);
    void deleteEquipment(String id);
    EquipmentResponse getEquipmentById(String id);
    List<EquipmentDTO> getAllEquipments();
    List<EquipmentDTO> getInUseFieldEquipments(String fieldCode);
    List<EquipmentDTO> getAvailableEquipments();
    void updateFieldEquipments(String fieldCode, List<String> equipmentIds);
}
