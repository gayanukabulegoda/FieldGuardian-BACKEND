package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.impl.FieldDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.service.FieldService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.DataConversionUtil;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {
    private final FieldRepository fieldRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveField(FieldDTO fieldDTO) {
        fieldDTO.setCode(customIdGenerator.generateId(IdPrefix.FIELD.getPrefix()));
        try {
            Field field = mapping.convertToEntity(fieldDTO, Field.class);
            field.setLocation(DataConversionUtil.toPoint(fieldDTO.getLocation()));
            field.setFieldImage1(DataConversionUtil.toBase64(fieldDTO.getFieldImage1()));
            field.setFieldImage2(DataConversionUtil.toBase64(fieldDTO.getFieldImage2()));
            fieldRepository.save(field);
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert file", e);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Field", e);
        }
    }

    @Override
    @Transactional
    public void updateField(String id, FieldDTO fieldDTO) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        field.setName(fieldDTO.getName());
        field.setExtentSize(fieldDTO.getExtentSize());
        try {
            field.setLocation(DataConversionUtil.toPoint(fieldDTO.getLocation()));
            field.setFieldImage1(DataConversionUtil.toBase64(fieldDTO.getFieldImage1()));
            field.setFieldImage2(DataConversionUtil.toBase64(fieldDTO.getFieldImage2()));
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert file", e);
        }
    }

    @Override
    public void deleteField(String id) {
        if (!fieldRepository.existsById(id))
            throw new FieldNotFoundException("Field not found");
        fieldRepository.deleteById(id);
    }

    @Override
    public FieldResponse getFieldById(String id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new FieldNotFoundException("Field not found"));
        return mapping.convertToDTO(field, FieldDTO.class);
    }

    @Override
    public List<FieldDTO> getAllFields() {
        List<Field> fields = fieldRepository.findAll();
        if (fields.isEmpty()) throw new FieldNotFoundException("No fields found");
        return mapping.convertToDTOList(fields, FieldDTO.class);
    }
}
