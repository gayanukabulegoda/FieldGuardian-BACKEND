package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.EquipmentResponse;
import lk.ijse.fieldguardianbackend.dto.impl.EquipmentDTO;
import lk.ijse.fieldguardianbackend.dto.impl.UpdateEquipmentStaffDTO;
import lk.ijse.fieldguardianbackend.entity.enums.EquipmentStatus;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Equipment;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.EquipmentNotFoundException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.repository.EquipmentRepository;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.service.EquipmentService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;
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
            throw new DataPersistFailedException("Cannot Save Equipment", 0, e);
        }
    }

    @Override
    @Transactional
    public void updateEquipment(String id, EquipmentDTO equipmentDTO) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found"));
        equipment.setName(equipmentDTO.getName());
        equipment.setType(equipmentDTO.getType());
    }

    @Override
    @Transactional
    public void updateFieldEquipments(String fieldCode, List<String> equipmentIds) {
        Field field = fieldRepository.findByIdAndStatusNot(fieldCode, Status.REMOVED)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        List<Equipment> equipments = equipmentRepository.findAllByStatusNot(EquipmentStatus.OUT_OF_SERVICE);
        if (equipments.isEmpty())
            throw new EquipmentNotFoundException("No equipment found for the provided IDs");
        for (Equipment equipment : equipments) {
            equipment.setAssignedField(field);
            equipment.setStatus(EquipmentStatus.IN_USE);
        }
    }

    @Override
    @Transactional
    public void updateEquipmentStaff(UpdateEquipmentStaffDTO updateEquipmentStaffDTO) {
        Equipment equipment = equipmentRepository
                .findByIdAndStatusNot(updateEquipmentStaffDTO.getEquipmentId(), EquipmentStatus.OUT_OF_SERVICE)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found"));
        Staff staff = staffRepository.findActiveStaffById(updateEquipmentStaffDTO.getStaffId(), Status.ACTIVE)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        if (equipment.getAssignedStaff() != null && !equipment.getAssignedStaff().getId().equals(staff.getId()))
            throw new DataPersistFailedException("Equipment already assigned to another staff", 1);
        equipment.setAssignedStaff(staff);
    }

    @Override
    @Transactional
    public void deleteEquipment(String id) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found"));
        equipment.setStatus(EquipmentStatus.OUT_OF_SERVICE);
    }

    @Override
    public EquipmentResponse getEquipmentById(String id) {
        Equipment equipment = equipmentRepository.findByIdAndStatusNot(id, EquipmentStatus.OUT_OF_SERVICE)
                .orElseThrow(() -> new EquipmentNotFoundException("Equipment not found"));
        return mapping.convertToDTO(equipment, EquipmentDTO.class);
    }

    @Override
    public List<EquipmentDTO> getAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAllByStatusNot(EquipmentStatus.OUT_OF_SERVICE);
        if (equipments.isEmpty()) throw new EquipmentNotFoundException("No Equipment found");
        return mapping.convertToDTOList(equipments, EquipmentDTO.class);
    }

    @Override
    public List<EquipmentDTO> getAvailableEquipments() {
        List<Equipment> availableEquipments = equipmentRepository.findAllByStatus(EquipmentStatus.AVAILABLE);
        if (availableEquipments.isEmpty()) throw new EquipmentNotFoundException("No available equipment found");
        return mapping.convertToDTOList(availableEquipments, EquipmentDTO.class);
    }

    @Override
    public List<EquipmentDTO> getInUseFieldEquipments(String fieldCode) {
        if (!fieldRepository.existsById(fieldCode)) throw new FieldNotFoundException("Field not found");
        List<Equipment> equipments = equipmentRepository.findByFieldCode(fieldCode);
        if (equipments.isEmpty()) throw new EquipmentNotFoundException("No in use Equipment found for the field");
        return mapping.convertToDTOList(equipments, EquipmentDTO.class);
    }
}