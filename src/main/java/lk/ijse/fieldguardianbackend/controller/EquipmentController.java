package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.EquipmentResponse;
import lk.ijse.fieldguardianbackend.dto.impl.EquipmentDTO;
import lk.ijse.fieldguardianbackend.dto.impl.UpdateEquipmentStaffDTO;
import lk.ijse.fieldguardianbackend.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing Equipment.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/equipment")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EquipmentController {
    private final EquipmentService equipmentService;
    /**
     * {@code POST /} : Create a new equipment.
     *
     * @param equipmentDTO the equipment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveEquipment(@Valid @RequestBody EquipmentDTO equipmentDTO) {
        log.info("Request to save equipment: {}", equipmentDTO);
        equipmentService.saveEquipment(equipmentDTO);
        log.info("Request to save equipment: {}", equipmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH /{id}} : Updates an existing equipment.
     *
     * @param id the id of the equipment to update.
     * @param equipmentDTO the equipment to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipment(@PathVariable("id") String id, @Valid @RequestBody EquipmentDTO equipmentDTO) {
        log.info("Request to update equipment with id: {}", id);
        equipmentService.updateEquipment(id, equipmentDTO);
        log.info("Equipment with id: {} updated successfully", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code PATCH /field/{fieldCode}} : Updates the equipments for a specific field.
     *
     * @param fieldCode the code of the field.
     * @param equipmentIds the list of equipment ids to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PatchMapping(value = "/field/{fieldCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateFieldEquipments(@PathVariable("fieldCode") String fieldCode, @RequestBody List<String> equipmentIds) {
        log.info("Request to update field equipments for fieldCode: {}", fieldCode);
        equipmentService.updateFieldEquipments(fieldCode, equipmentIds);
        log.info("Field equipments for fieldCode: {} updated successfully", fieldCode);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code PATCH /update-staff} : Updates the staff assigned to equipment.
     *
     * @param updateEquipmentStaffDTO the DTO containing equipment id and staff id.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PatchMapping(value = "/update-staff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateEquipmentStaff(@Valid @RequestBody UpdateEquipmentStaffDTO updateEquipmentStaffDTO) {
        log.info("Request to update equipment staff: {}", updateEquipmentStaffDTO);
        equipmentService.updateEquipmentStaff(updateEquipmentStaffDTO);
        log.info("Equipment staff updated successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code DELETE /{id}} : Deletes an equipment.
     *
     * @param id the id of the equipment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEquipment(@PathVariable("id") String id) {
        log.info("Request to delete equipment with id: {}", id);
        equipmentService.deleteEquipment(id);
        log.info("Equipment with id: {} deleted successfully", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code GET /{id}} : Get equipment by id.
     *
     * @param id the id of the equipment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipment.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EquipmentResponse> getEquipment(@PathVariable("id") String id) {
        log.info("Request to get equipment with id: {}", id);
        EquipmentResponse response = equipmentService.getEquipmentById(id);
        log.info("Equipment with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * {@code GET /} : Get all equipments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of equipments.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getAllEquipments() {
        log.info("Request to get all equipments");
        List<EquipmentDTO> response = equipmentService.getAllEquipments();
        log.info("All equipments retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * {@code GET /available} : Get all available equipments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of available equipments.
     */
    @GetMapping(value = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getAvailableEquipments() {
        log.info("Request to get available equipments");
        List<EquipmentDTO> response = equipmentService.getAvailableEquipments();
        log.info("Available equipments retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * {@code GET /field/{fieldCode}} : Get all in-use equipments for a specific field.
     *
     * @param fieldCode the code of the field.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of in-use equipments.
     */
    @GetMapping(value = "/field/{fieldCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EquipmentDTO>> getInUseFieldEquipments(@PathVariable("fieldCode") String fieldCode) {
        log.info("Request to get in-use field equipments for fieldCode: {}", fieldCode);
        List<EquipmentDTO> response = equipmentService.getInUseFieldEquipments(fieldCode);
        log.info("In-use field equipments for fieldCode: {} retrieved successfully", fieldCode);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}