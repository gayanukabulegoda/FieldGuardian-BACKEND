package lk.ijse.fieldguardianbackend.service;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CropService {
    void saveCrop(CropDTO cropDTO);
    void updateCrop(String id, CropDTO cropDTO);
    void deleteCrop(String id);
    CropResponse getSelectedCrop(String id);
    List<CropDTO> getAllCrops();
}
