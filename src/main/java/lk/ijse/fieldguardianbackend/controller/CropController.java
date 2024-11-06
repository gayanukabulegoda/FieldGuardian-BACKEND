package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.CropErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.FieldErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropResponseDTO;
import lk.ijse.fieldguardianbackend.dto.impl.CropSaveDTO;
import lk.ijse.fieldguardianbackend.exception.CropNotFoundException;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import lk.ijse.fieldguardianbackend.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crop")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CropController {
    private final CropService cropService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(@ModelAttribute CropSaveDTO cropSaveDTO) {
        cropService.saveCrop(cropSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable("id") String id, @ModelAttribute CropSaveDTO cropSaveDTO) {
        cropService.updateCrop(id, cropSaveDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("id") String id) {
        cropService.deleteCrop(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropResponse> getCrop(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(cropService.getSelectedCrop(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropResponseDTO>> getAllCrops() {
        return ResponseEntity.status(HttpStatus.OK).body(cropService.getAllCrops());
    }

    @ExceptionHandler(FileConversionException.class)
    public ResponseEntity<CropResponse> handleFileConversionException(FileConversionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CropErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<CropResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CropErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(CropNotFoundException.class)
    public ResponseEntity<CropResponse> handleCropNotFoundException(CropNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CropErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<FieldResponse> handleFieldNotFoundException(FieldNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FieldErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CropResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CropErrorResponse(0, e.getMessage()));
    }
}