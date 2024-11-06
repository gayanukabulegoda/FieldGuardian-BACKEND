package lk.ijse.fieldguardianbackend.util;

import jakarta.annotation.PostConstruct;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper.addConverter(ModelMapperConverter.multipartFileToBase64Converter());
        modelMapper.addConverter(ModelMapperConverter.stringToPointConverter());
    }

    public <T> T convertToDTO(Object entity, Class<T> dtoClass) {
        if (dtoClass == null) return null;
        return modelMapper.map(entity, dtoClass);
    }
    public <T> T convertToEntity(Object dto, Class<T> entityClass) {
        if (entityClass == null) return null;
        return modelMapper.map(dto, entityClass);
    }
    public <T> List<T> convertToDTOList(Object entityList, Class<T> dtoClass) {
        if (entityList == null) return null;
        if (dtoClass == VehicleDTO.class)
            return modelMapper.map(entityList, new TypeToken<List<VehicleDTO>>() {}.getType());
        if (dtoClass == FieldResponseDTO.class)
            return modelMapper.map(entityList, new TypeToken<List<FieldResponseDTO>>() {}.getType());
        if (dtoClass == EquipmentDTO.class)
            return modelMapper.map(entityList, new TypeToken<List<EquipmentDTO>>() {}.getType());
        if (dtoClass == CropResponseDTO.class)
            return modelMapper.map(entityList, new TypeToken<List<CropResponseDTO>>() {}.getType());
        if (dtoClass == MonitoringLogResponseDTO.class)
            return modelMapper.map(entityList, new TypeToken<List<MonitoringLogResponseDTO>>() {}.getType());
        return modelMapper.map(entityList, new TypeToken<List<T>>() {}.getType());
    }
}