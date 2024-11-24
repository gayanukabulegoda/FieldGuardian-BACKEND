package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import lk.ijse.fieldguardianbackend.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing fields.
 */
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
    @PreAuthorize("hasAnyRole('MANAGER', 'SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(@Valid @ModelAttribute FieldSaveDTO fieldSaveDTO) {
        fieldService.saveField(fieldSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH /{id}} : Updates an existing field.
     *
     * @param id the id of the field to update.
     * @param fieldSaveDTO the field to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'SCIENTIST')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("id") String id, @Valid @ModelAttribute FieldSaveDTO fieldSaveDTO) {
        fieldService.updateField(id, fieldSaveDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code PATCH /{id}/staff} : Updates the staff of an existing field.
     *
     * @param id the id of the field to update.
     * @param staffIds the list of staff ids to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'SCIENTIST')")
    @PatchMapping(value = "/{id}/staff", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateFieldStaff(@PathVariable("id") String id, @RequestBody List<String> staffIds) {
        fieldService.updateFieldStaff(id, staffIds);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code DELETE /{id}} : Deletes an existing field.
     *
     * @param id the id of the field to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'SCIENTIST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable("id") String id) {
        fieldService.deleteField(id);
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
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldById(id));
    }
    /**
     * {@code GET /} : Get all fields.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fields.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldResponseDTO>> getAllFields() {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getAllFields());
    }
    /**
     * {@code GET /{id}/staff} : Get the staff of a field by id.
     *
     * @param id the id of the field to retrieve staff for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of staff.
     */
    @GetMapping(value = "/{id}/staff", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getFieldStaff(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldStaff(id));
    }
    /**
     * {@code GET /{id}/crops} : Get the crops of a field by id.
     *
     * @param fieldId the id of the field to retrieve crops for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of crops.
     */
    @GetMapping(value = "/{id}/crops", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropResponseDTO>> getFieldCrops(@PathVariable("id") String fieldId) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldCrops(fieldId));
    }
}