package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import lk.ijse.fieldguardianbackend.service.FieldService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing fields.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FieldController {
    private final FieldService fieldService;
    /**
     * {@code POST /} : Create a new field.
     *
     * @param fieldSaveDTO the field to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)}.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(@Valid @ModelAttribute FieldSaveDTO fieldSaveDTO) {
        log.info("Request to save field: {}", fieldSaveDTO);
        fieldService.saveField(fieldSaveDTO);
        log.info("Field saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH /{id}} : Updates an existing field.
     *
     * @param id the id of the field to update.
     * @param fieldSaveDTO the field to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("id") String id, @Valid @ModelAttribute FieldSaveDTO fieldSaveDTO) {
        log.info("Request to update field with id: {}", id);
        fieldService.updateField(id, fieldSaveDTO);
        log.info("Field with id: {} updated successfully", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code PATCH /{id}/staff} : Updates the staff of an existing field.
     *
     * @param id the id of the field to update.
     * @param staffIds the list of staff ids to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PatchMapping(value = "/{id}/staff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateFieldStaff(@PathVariable("id") String id, @RequestBody List<String> staffIds) {
        log.info("Request to update staff for field with id: {}", id);
        fieldService.updateFieldStaff(id, staffIds);
        log.info("Staff for field with id: {} updated successfully", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code DELETE /{id}} : Deletes an existing field.
     *
     * @param id the id of the field to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable("id") String id) {
        log.info("Request to delete field with id: {}", id);
        fieldService.deleteField(id);
        log.info("Field with id: {} deleted successfully", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code GET /{id}} : Get the field by id.
     *
     * @param id the id of the field to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the field.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldResponse> getField(@PathVariable("id") String id) {
        log.info("Request to get field with id: {}", id);
        FieldResponse fieldResponse = fieldService.getFieldById(id);
        log.info("Field with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(fieldResponse);
    }
    /**
     * {@code GET /} : Get all fields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fields.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldResponseDTO>> getAllFields() {
        log.info("Request to get all fields");
        List<FieldResponseDTO> fields = fieldService.getAllFields();
        log.info("All fields retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(fields);
    }
    /**
     * {@code GET /{id}/staff} : Get the staff of a field by id.
     *
     * @param id the id of the field to retrieve staff for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of staff.
     */
    @GetMapping(value = "/{id}/staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getFieldStaff(@PathVariable("id") String id) {
        log.info("Request to get staff for field with id: {}", id);
        List<StaffDTO> staff = fieldService.getFieldStaff(id);
        log.info("Staff for field with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(staff);
    }
    /**
     * {@code GET /{id}/crops} : Get the crops of a field by id.
     *
     * @param fieldId the id of the field to retrieve crops for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of crops.
     */
    @GetMapping(value = "/{id}/crops", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropResponseDTO>> getFieldCrops(@PathVariable("id") String fieldId) {
        log.info("Request to get crops for field with id: {}", fieldId);
        List<CropResponseDTO> crops = fieldService.getFieldCrops(fieldId);
        log.info("Crops for field with id: {} retrieved successfully", fieldId);
        return ResponseEntity.status(HttpStatus.OK).body(crops);
    }
}