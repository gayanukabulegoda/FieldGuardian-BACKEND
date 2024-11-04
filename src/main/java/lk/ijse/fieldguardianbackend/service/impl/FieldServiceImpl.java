package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropDTO;
import lk.ijse.fieldguardianbackend.dto.impl.FieldResponseDTO;
import lk.ijse.fieldguardianbackend.dto.impl.FieldSaveDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.exception.*;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.service.FieldService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.DataConversionUtil;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveField(FieldSaveDTO fieldSaveDTO) {
        fieldSaveDTO.setCode(customIdGenerator.generateId(IdPrefix.FIELD.getPrefix()));
        try {
            Field field = mapping.convertToEntity(fieldSaveDTO, Field.class);
            field.setLocation(DataConversionUtil.toPoint(fieldSaveDTO.getLocation()));
            field.setFieldImage1(DataConversionUtil.toBase64(fieldSaveDTO.getFieldImage1()));
            field.setFieldImage2(DataConversionUtil.toBase64(fieldSaveDTO.getFieldImage2()));
            field.setStatus(Status.ACTIVE);
            fieldRepository.save(field);
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert file", e);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Field", 0, e);
        }
    }

    @Override
    @Transactional
    public void updateField(String id, FieldSaveDTO fieldSaveDTO) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        field.setName(fieldSaveDTO.getName());
        field.setExtentSize(fieldSaveDTO.getExtentSize());
        try {
            field.setLocation(DataConversionUtil.toPoint(fieldSaveDTO.getLocation()));
            field.setFieldImage1(DataConversionUtil.toBase64(fieldSaveDTO.getFieldImage1()));
            field.setFieldImage2(DataConversionUtil.toBase64(fieldSaveDTO.getFieldImage2()));
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert file", e);
        }
    }

    @Override
    @Transactional
    public void updateFieldStaff(String fieldId, List<String> staffIds) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        List<Staff> staffList = staffRepository.findAllById(staffIds);
        field.setStaff(staffList);
    }

    @Override
    @Transactional
    public void deleteField(String id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        field.setStatus(Status.REMOVED);
    }

    @Override
    public FieldResponse getFieldById(String id) {
        Field field = fieldRepository.findByIdAndStatusNot(id, Status.REMOVED)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        return mapping.convertToDTO(field, FieldResponseDTO.class);
    }

    @Override
    public List<FieldResponseDTO> getAllFields() {
        List<Field> fields = fieldRepository.findAllByStatusNot(Status.REMOVED);
        if (fields.isEmpty()) throw new FieldNotFoundException("No fields found");
        return mapping.convertToDTOList(fields, FieldResponseDTO.class);
    }

    @Override
    public List<StaffDTO> getFieldStaff(String fieldId) {
        Field field = fieldRepository.findById(fieldId)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        List<Staff> staffList = field.getStaff();
        if (staffList.isEmpty())
            throw new StaffNotFoundException("No staff found for the given field");
        return staffList.stream()
                .map(staff -> mapping.convertToDTO(staff, StaffDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CropDTO> getFieldCrops(String fieldId) {
        List<Crop> crops = fieldRepository.findCropsByFieldId(fieldId, Status.REMOVED);
        if (crops.isEmpty()) throw new CropNotFoundException("No crops found for the field");
        return mapping.convertToDTOList(crops, CropDTO.class);
    }
}