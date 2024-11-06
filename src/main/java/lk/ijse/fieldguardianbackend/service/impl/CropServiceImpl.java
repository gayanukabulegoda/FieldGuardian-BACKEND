package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropResponseDTO;
import lk.ijse.fieldguardianbackend.dto.impl.CropSaveDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.enums.Status;
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
    public void saveCrop(CropSaveDTO cropSaveDTO) {
        cropSaveDTO.setCode(customIdGenerator.generateId(IdPrefix.CROP.getPrefix()));
        try {
            Crop crop = mapping.convertToEntity(cropSaveDTO, Crop.class);
            crop.setCropImage(Optional.ofNullable(cropSaveDTO.getCropImage())
                    .map(DataConversionUtil::toBase64)
                    .orElse(null));
            crop.setStatus(Status.ACTIVE);
            cropRepository.save(crop);
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Crop", 0, e);
        }
    }

    @Override
    @Transactional
    public void updateCrop(String id, CropSaveDTO cropSaveDTO) {
        Crop crop = cropRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new CropNotFoundException("Crop not found"));
        crop.setCommonName(cropSaveDTO.getCommonName());
        crop.setScientificName(cropSaveDTO.getScientificName());
        try {
            crop.setCropImage(Optional.ofNullable(cropSaveDTO.getCropImage())
                    .map(DataConversionUtil::toBase64)
                    .orElse(null));
        } catch (Exception e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        }
        crop.setCategory(cropSaveDTO.getCategory());
        crop.setSeason(cropSaveDTO.getSeason());
        crop.setField(fieldRepository.findById(cropSaveDTO.getFieldCode())
                .orElseThrow(() -> new FieldNotFoundException("Field not found")));
    }

    @Override
    @Transactional
    public void deleteCrop(String id) {
        Crop crop = cropRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new CropNotFoundException("Crop not found"));
        crop.setStatus(Status.REMOVED);
    }

    @Override
    public CropResponse getSelectedCrop(String id) {
        Crop crop = cropRepository.findByIdAndStatus(id, Status.ACTIVE)
                .orElseThrow(() -> new CropNotFoundException("Crop not found"));
        return mapping.convertToDTO(crop, CropResponseDTO.class);
    }

    @Override
    public List<CropResponseDTO> getAllCrops() {
        List<Crop> crops = cropRepository.findAllByStatus(Status.ACTIVE);
        if (crops.isEmpty()) throw new CropNotFoundException("No Crops found");
        return mapping.convertToDTOList(crops, CropResponseDTO.class);
    }
}