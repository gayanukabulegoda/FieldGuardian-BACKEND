package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.exception.CropNotFoundException;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import lk.ijse.fieldguardianbackend.repository.CropRepository;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.service.CropService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.DataConversionUtil;
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
            Crop crop = mapping.convertToEntity(cropDTO, Crop.class);
            crop.setCropImage(Optional.ofNullable(cropDTO.getCropImage())
                    .map(DataConversionUtil::toBase64)
                    .orElse(null));
            cropRepository.save(crop);
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert image to base64", e);
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
        try {
            crop.setCropImage(Optional.ofNullable(cropDTO.getCropImage())
                    .map(DataConversionUtil::toBase64)
                    .orElse(null));
        } catch (Exception e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        }
        crop.setCategory(cropDTO.getCategory());
        crop.setSeason(cropDTO.getSeason());
        crop.setField(fieldRepository.findById(cropDTO.getFieldCode())
                .orElseThrow(() -> new FieldNotFoundException("Field not found")));
    }

    @Override
    public void deleteCrop(String id) {
        if (!cropRepository.existsById(id))
            throw new CropNotFoundException("Crop not found");
        cropRepository.deleteById(id);
    }

    @Override
    public CropResponse getSelectedCrop(String id) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new CropNotFoundException("Crop not found"));
        return mapping.convertToDTO(crop, CropDTO.class);
    }

    @Override
    public List<CropDTO> getAllCrops() {
        List<Crop> crops = cropRepository.findAll();
        if (crops.isEmpty()) throw new CropNotFoundException("No Crops found");
        return mapping.convertToDTOList(crops, CropDTO.class);
    }
}
