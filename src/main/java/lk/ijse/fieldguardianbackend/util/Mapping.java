package lk.ijse.fieldguardianbackend.util;

import jakarta.annotation.PostConstruct;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;
import java.util.List;
/**
 * Utility class for mapping between DTOs and entities using ModelMapper.
 */
@Component
@RequiredArgsConstructor
public class Mapping {
    private final ModelMapper modelMapper;
    /**
     * Initializes custom converters for ModelMapper.
     */
    @PostConstruct
    public void init() {
        modelMapper.addConverter(ModelMapperConverter.multipartFileToBase64Converter());
        modelMapper.addConverter(ModelMapperConverter.stringToPointConverter());
    }
    /**
     * Converts an entity to a DTO.
     *
     * @param entity the entity to convert
     * @param dtoClass the class of the DTO
     * @param <T> the type of the DTO
     * @return the converted DTO
     */
    public <T> T convertToDTO(Object entity, Class<T> dtoClass) {
        if (dtoClass == null) return null;
        return modelMapper.map(entity, dtoClass);
    }
    /**
     * Converts a DTO to an entity.
     *
     * @param dto the DTO to convert
     * @param entityClass the class of the entity
     * @param <T> the type of the entity
     * @return the converted entity
     */
    public <T> T convertToEntity(Object dto, Class<T> entityClass) {
        if (entityClass == null) return null;
        return modelMapper.map(dto, entityClass);
    }
    /**
     * Converts a list of entities to a list of DTOs.
     *
     * @param entityList the list of entities to convert
     * @param dtoClass the class of the DTOs
     * @param <T> the type of the DTOs
     * @return the list of converted DTOs
     */
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
        if (dtoClass == UserResponseDTO.class)
            return modelMapper.map(entityList, new TypeToken<List<UserResponseDTO>>() {}.getType());
        return modelMapper.map(entityList, new TypeToken<List<T>>() {}.getType());
    }
}