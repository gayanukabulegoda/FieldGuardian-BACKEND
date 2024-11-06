package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.EquipmentResponse;
import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.EquipmentErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.FieldErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.StaffErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.EquipmentDTO;
import lk.ijse.fieldguardianbackend.dto.impl.UpdateEquipmentStaffDTO;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.EquipmentNotFoundException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@RequestBody EquipmentDTO equipmentDTO) {
        equipmentService.saveEquipment(equipmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable("id") String id, @RequestBody EquipmentDTO equipmentDTO) {
        equipmentService.updateEquipment(id, equipmentDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/field/{fieldCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateFieldEquipments(@PathVariable("fieldCode") String fieldCode, @RequestBody List<String> equipmentIds) {
        equipmentService.updateFieldEquipments(fieldCode, equipmentIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/update-staff")
    public ResponseEntity<Void> updateEquipmentStaff(@RequestBody UpdateEquipmentStaffDTO updateEquipmentStaffDTO) {
        equipmentService.updateEquipmentStaff(updateEquipmentStaffDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") String id) {
        equipmentService.deleteEquipment(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentResponse> getEquipment(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.getEquipmentById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.getAllEquipments());
    }

    @GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getAvailableEquipments() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.getAvailableEquipments());
    }

    @GetMapping(value = "/field/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getInUseFieldEquipments(@PathVariable("fieldCode") String fieldCode) {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.getInUseFieldEquipments(fieldCode));
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<EquipmentResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new EquipmentErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(EquipmentNotFoundException.class)
    public ResponseEntity<EquipmentResponse> handleEquipmentNotFoundException(EquipmentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new EquipmentErrorResponse(0, e.getMessage()));
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EquipmentResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new EquipmentErrorResponse(0, e.getMessage()));
    }
}