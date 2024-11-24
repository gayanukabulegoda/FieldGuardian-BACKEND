package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.dto.impl.CropResponseDTO;
import lk.ijse.fieldguardianbackend.dto.impl.CropSaveDTO;
import lk.ijse.fieldguardianbackend.service.CropService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing crops.
 */
@RestController
@RequestMapping("/api/v1/crop")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CropController {
    private final CropService cropService;
    /**
     * {@code POST /} : Create a new crop.
     *
     * @param cropSaveDTO the crop to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'SCIENTIST')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveCrop(@Valid @ModelAttribute CropSaveDTO cropSaveDTO) {
        cropService.saveCrop(cropSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH /{id}} : Updates an existing crop.
     *
     * @param id the id of the crop to update.
     * @param cropSaveDTO the crop to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'SCIENTIST')")
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateCrop(@PathVariable("id") String id, @Valid @ModelAttribute CropSaveDTO cropSaveDTO) {
        cropService.updateCrop(id, cropSaveDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code DELETE /{id}} : Deletes an existing crop.
     *
     * @param id the id of the crop to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'SCIENTIST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("id") String id) {
        cropService.deleteCrop(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code GET /{id}} : Get the crop by id.
     *
     * @param id the id of the crop to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crop.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CropResponse> getCrop(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(cropService.getSelectedCrop(id));
    }
    /**
     * {@code GET /} : Get all crops.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all crops.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CropResponseDTO>> getAllCrops() {
        return ResponseEntity.status(HttpStatus.OK).body(cropService.getAllCrops());
    }
}