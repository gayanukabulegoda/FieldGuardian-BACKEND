package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.CropErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.FieldErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.StaffErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropDTO;
import lk.ijse.fieldguardianbackend.dto.impl.FieldResponseDTO;
import lk.ijse.fieldguardianbackend.dto.impl.FieldSaveDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.exception.*;
import lk.ijse.fieldguardianbackend.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FieldController {
    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(@ModelAttribute FieldSaveDTO fieldSaveDTO) {
        fieldService.saveField(fieldSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("id") String id, @ModelAttribute FieldSaveDTO fieldSaveDTO) {
        fieldService.updateField(id, fieldSaveDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/{id}/staff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateFieldStaff(@PathVariable("id") String id, @RequestBody List<String> staffIds) {
        fieldService.updateFieldStaff(id, staffIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable("id") String id) {
        fieldService.deleteField(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldResponse> getField(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldResponseDTO>> getAllFields() {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getAllFields());
    }

    @GetMapping(value = "/{id}/staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getFieldStaff(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldStaff(id));
    }

    @GetMapping(value = "/{id}/crops", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropDTO>> getFieldCrops(@PathVariable("id") String fieldId) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldCrops(fieldId));
    }

    @ExceptionHandler(FileConversionException.class)
    public ResponseEntity<FieldResponse> handleFileConversionException(FileConversionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new FieldErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<FieldResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new FieldErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<FieldResponse> handleFieldNotFoundException(FieldNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FieldErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<StaffResponse> handleStaffNotFoundException(StaffNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StaffErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(CropNotFoundException.class)
    public ResponseEntity<CropResponse> handleCropNotFoundException(CropNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CropErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FieldResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new FieldErrorResponse(0, e.getMessage()));
    }
}