package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropResponseDTO;
import lk.ijse.fieldguardianbackend.dto.impl.CropSaveDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CropService {
    void saveCrop(CropSaveDTO cropSaveDTO);
    void updateCrop(String id, CropSaveDTO cropSaveDTO);
    void deleteCrop(String id);
    CropResponse getSelectedCrop(String id);
    List<CropResponseDTO> getAllCrops();
}
