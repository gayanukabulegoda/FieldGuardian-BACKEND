package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.FieldErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.FieldDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.service.FieldService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
            fieldRepository.save(mapping.convertToEntity(fieldDTO, Field.class));
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
        field.setLocation(fieldDTO.getLocation());
        field.setExtentSize(fieldDTO.getExtentSize());
        field.setFieldImage1(fieldDTO.getFieldImage1());
        field.setFieldImage2(fieldDTO.getFieldImage2());
    }

    @Override
    public void deleteField(String id) {
        if (!fieldRepository.existsById(id)) {
            throw new FieldNotFoundException("Field not found");
        }
        fieldRepository.deleteById(id);
    }

    @Override
    public FieldResponse getFieldById(String id) {
        Optional<Field> byId = fieldRepository.findById(id);
        return (byId.isPresent())
                ? mapping.convertToDTO(byId.get(), FieldDTO.class)
                : new FieldErrorResponse(0, "Field not found");
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return mapping.convertToDTOList(fieldRepository.findAll(), FieldDTO.class);
    }
}
