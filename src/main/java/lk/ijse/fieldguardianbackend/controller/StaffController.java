package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffFieldDTO;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.entity.enums.Designation;
import lk.ijse.fieldguardianbackend.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing staff-related operations.
 */
@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
public class StaffController {
    private final StaffService staffService;
    /**
     * {@code POST /} : Create a new staff.
     *
     * @param staffDTO the staffDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@Valid @RequestBody StaffDTO staffDTO) {
        staffService.saveStaff(staffDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH /{id}} : Updates an existing staff.
     *
     * @param id the id of the staff to update.
     * @param staffDTO the staffDTO to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("id") String id, @Valid @RequestBody StaffDTO staffDTO) {
        staffService.updateStaff(id, staffDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code DELETE /{id}} : Deletes an existing staff.
     *
     * @param id the id of the staff to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("id") String id) {
        staffService.deleteStaff(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code GET /{id}} : Get the "id" staff.
     *
     * @param id the id of the staff to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the staffResponse.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffResponse> getStaff(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getSelectedStaff(id));
    }
    /**
     * {@code GET /} : Get all staff.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staff in body.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getAllStaffs());
    }
    /**
     * {@code GET /{id}/vehicles} : Get the vehicles of the "id" staff.
     *
     * @param id the id of the staff to retrieve vehicles for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping(value = "/{id}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getStaffVehicles(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getStaffVehicles(id));
    }
    /**
     * {@code GET /{id}/vehicles} : Get the vehicles of the "id" staff.
     *
     * @param id the id of the staff to retrieve vehicles for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping(value = "/{id}/fields", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffFieldDTO>> getStaffFields(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getStaffFields(id));
    }
    /**
     * {@code GET /without-equipment} : Get all staff without equipment.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staff without equipment in body.
     */
    @GetMapping(value = "/without-equipment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getStaffWithoutEquipment() {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getStaffWithoutEquipment());
    }
    /**
     * {@code GET /designations} : Get all staff designations.
     * @return the {@link List} of staff designations.
     */
    @GetMapping("/designations")
    public List<Designation> getAllStaffDesignations() {
        return staffService.getAllStaffDesignations();
    }
}