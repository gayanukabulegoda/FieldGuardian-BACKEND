package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.EquipmentResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.EquipmentErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.EquipmentDTO;
import lk.ijse.fieldguardianbackend.entity.enums.EquipmentStatus;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.Equipment;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.EquipmentNotFoundException;
import lk.ijse.fieldguardianbackend.repository.EquipmentRepository;
import lk.ijse.fieldguardianbackend.service.EquipmentService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveEquipment(EquipmentDTO equipmentDTO) {
        equipmentDTO.setId(customIdGenerator.generateId(IdPrefix.EQUIPMENT.getPrefix()));
        Equipment equipment = mapping.convertToEntity(equipmentDTO, Equipment.class);
        equipment.setStatus(EquipmentStatus.AVAILABLE);
        try {
            equipmentRepository.save(equipment);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Equipment", e);
        }
    }

    @Override
    @Transactional
    public void updateEquipment(String id, EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
        equipment.setName(equipmentDTO.getName());
        equipment.setType(equipmentDTO.getType());
    }

    @Override
    public void deleteEquipment(String id) {
        if (!equipmentRepository.existsById(id)) {
            throw new EquipmentNotFoundException("Equipment not found");
        }
        equipmentRepository.deleteById(id);
    }

    @Override
    public EquipmentResponse getEquipmentById(String id) {
        Optional<Equipment> byId = equipmentRepository.findById(id);
        return (byId.isPresent())
                ? mapping.convertToDTO(byId.get(), EquipmentDTO.class)
                : new EquipmentErrorResponse(0, "Equipment not found");
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        return mapping.convertToDTOList(equipmentRepository.findAll(), EquipmentDTO.class);
    }
}
