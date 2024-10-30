package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.CropErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.exception.CropNotFoundException;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.repository.CropRepository;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.service.CropService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CropServiceImpl implements CropService {
    private final CropRepository cropRepository;
    private final FieldRepository fieldRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveCrop(CropDTO cropDTO) {
        cropDTO.setCode(customIdGenerator.generateId(IdPrefix.CROP.getPrefix()));
        try {
            cropRepository.save(mapping.convertToEntity(cropDTO, Crop.class));
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Crop", e);
        }
    }

    @Override
    @Transactional
    public void updateCrop(String id, CropDTO cropDTO) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new CropNotFoundException("Crop not found"));
        crop.setCommonName(cropDTO.getCommonName());
        crop.setScientificName(cropDTO.getScientificName());
        crop.setCropImage(cropDTO.getCropImage());
        crop.setCategory(cropDTO.getCategory());
        crop.setSeason(cropDTO.getSeason());
        crop.setField(fieldRepository.findById(cropDTO.getFieldCode())
                .orElseThrow(() -> new FieldNotFoundException("Field not found")));
    }

    @Override
    public void deleteCrop(String id) {
        if (!cropRepository.existsById(id)) {
            throw new CropNotFoundException("Crop not found");
        }
        cropRepository.deleteById(id);
    }

    @Override
    public CropResponse getSelectedCrop(String id) {
        Optional<Crop> byId = cropRepository.findById(id);
        return (byId.isPresent())
                ? mapping.convertToDTO(byId.get(), CropDTO.class)
                : new CropErrorResponse(0, "Crop not found");
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return mapping.convertToDTOList(cropRepository.findAll(), CropDTO.class);
    }
}
