package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffFieldDTO;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing staff-related operations.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {
    private final StaffService staffService;
    /**
     * {@code POST /} : Create a new staff.
     *
     * @param staffDTO the staffDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)}.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@Valid @RequestBody StaffDTO staffDTO) {
        log.info("Request to save staff: {}", staffDTO);
        staffService.saveStaff(staffDTO);
        log.info("Staff saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH /{id}} : Updates an existing staff.
     *
     * @param id the id of the staff to update.
     * @param staffDTO the staffDTO to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("id") String id, @Valid @RequestBody StaffDTO staffDTO) {
        log.info("Request to update staff with id: {}", id);
        staffService.updateStaff(id, staffDTO);
        log.info("Staff with id: {} updated successfully", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code DELETE /{id}} : Deletes an existing staff.
     *
     * @param id the id of the staff to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("id") String id) {
        log.info("Request to delete staff with id: {}", id);
        staffService.deleteStaff(id);
        log.info("Staff with id: {} deleted successfully", id);
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
        log.info("Request to get staff with id: {}", id);
        StaffResponse staffResponse = staffService.getSelectedStaff(id);
        log.info("Staff with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(staffResponse);
    }
    /**
     * {@code GET /} : Get all staff.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staff in body.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        log.info("Request to get all staff");
        List<StaffDTO> staffList = staffService.getAllStaffs();
        log.info("All staff retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(staffList);
    }
    /**
     * {@code GET /{id}/vehicles} : Get the vehicles of the "id" staff.
     *
     * @param id the id of the staff to retrieve vehicles for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping(value = "/{id}/vehicles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getStaffVehicles(@PathVariable("id") String id) {
        log.info("Request to get vehicles for staff with id: {}", id);
        List<VehicleDTO> vehicleList = staffService.getStaffVehicles(id);
        log.info("Vehicles for staff with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleList);
    }
    /**
     * {@code GET /{id}/vehicles} : Get the vehicles of the "id" staff.
     *
     * @param id the id of the staff to retrieve vehicles for.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping(value = "/{id}/fields", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffFieldDTO>> getStaffFields(@PathVariable("id") String id) {
        log.info("Request to get fields for staff with id: {}", id);
        List<StaffFieldDTO> fieldList = staffService.getStaffFields(id);
        log.info("Fields for staff with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(fieldList);
    }
    /**
     * {@code GET /without-equipment} : Get all staff without equipment.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staff without equipment in body.
     */
    @GetMapping(value = "/without-equipment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getStaffWithoutEquipment() {
        log.info("Request to get staff without equipment");
        List<StaffDTO> staffList = staffService.getStaffWithoutEquipment();
        log.info("Staff without equipment retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(staffList);
    }
}