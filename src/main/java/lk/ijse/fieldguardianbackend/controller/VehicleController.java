package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing vehicles.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService vehicleService;
    /**
     * {@code POST  /} : Create a new vehicle.
     *
     * @param vehicleDTO the vehicle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Request to save vehicle: {}", vehicleDTO);
        vehicleService.saveVehicle(vehicleDTO);
        log.info("Vehicle saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH  /:id} : Updates an existing vehicle.
     *
     * @param id the id of the vehicle to update.
     * @param vehicleDTO the vehicle to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable("id") String id, @Valid @RequestBody VehicleDTO vehicleDTO) {
        log.info("Request to update vehicle: {}", vehicleDTO);
        vehicleService.updateVehicle(id, vehicleDTO);
        log.info("Vehicle updated successfully with id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code PATCH  /:vehicleId/driver/:driverId} : Updates the driver of an existing vehicle.
     *
     * @param vehicleId the id of the vehicle to update.
     * @param driverId the id of the driver to assign.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @PatchMapping(value = "/{vehicleId}/driver/{driverId}")
    public ResponseEntity<Void> updateVehicleDriver(@PathVariable("vehicleId") String vehicleId, @PathVariable("driverId") String driverId) {
        log.info("Request to update vehicle driver with vehicleId: {} and driverId: {}", vehicleId, driverId);
        vehicleService.updateVehicleDriver(vehicleId, driverId);
        log.info("Driver updated successfully for vehicle with id: {}", vehicleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code DELETE  /:id} : Deletes an existing vehicle.
     *
     * @param id the id of the vehicle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMINISTRATIVE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") String id) {
        log.info("Request to delete vehicle with id: {}", id);
        vehicleService.deleteVehicle(id);
        log.info("Vehicle deleted successfully with id: {}", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code GET  /:id} : Get the "id" vehicle.
     *
     * @param id the id of the vehicle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vehicle.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable("id") String id) {
        log.info("Request to get vehicle with id: {}", id);
        VehicleResponse vehicleResponse = vehicleService.getSelectedVehicle(id);
        log.info("Vehicle retrieved successfully with id: {}", id);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleResponse);
    }
    /**
     * {@code GET  /} : Get all vehicles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        log.info("Request to get all vehicles");
        List<VehicleDTO> vehicles = vehicleService.getAllVehicles();
        log.info("All vehicles retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(vehicles);
    }
}