package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleFilterDTO;
import lk.ijse.fieldguardianbackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing vehicles.
 */
@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
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
        vehicleService.saveVehicle(vehicleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code POST  /filter} : Filter vehicles.
     *
     * @param filterDTO the filter to apply.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @PostMapping("/filter")
    public ResponseEntity<List<VehicleDTO>> filterVehicles(@RequestBody VehicleFilterDTO filterDTO) {
        List<VehicleDTO> filteredVehicles = vehicleService.filterVehicle(filterDTO);
        return ResponseEntity.ok(filteredVehicles);
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
        vehicleService.updateVehicle(id, vehicleDTO);
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
        vehicleService.updateVehicleDriver(vehicleId, driverId);
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
        vehicleService.deleteVehicle(id);
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
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getSelectedVehicle(id));
    }
    /**
     * {@code GET  /} : Get all vehicles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vehicles in body.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getAllVehicles());
    }
}