package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.StaffErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.VehicleErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.exception.VehicleNotFoundException;
import lk.ijse.fieldguardianbackend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicle")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VehicleController {
    private final VehicleService vehicleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveVehicle(@RequestBody VehicleDTO vehicleDTO) {
        vehicleService.saveVehicle(vehicleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateVehicle(@PathVariable("id") String id, @RequestBody VehicleDTO vehicleDTO) {
        vehicleService.updateVehicle(id, vehicleDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable("id") String id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleResponse> getVehicle(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getSelectedVehicle(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.getAllVehicles());
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<VehicleResponse> handleVehicleNotFoundException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new VehicleErrorResponse(0, "Data Persist Failed"));
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<VehicleResponse> handleVehicleNotFoundException(VehicleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new VehicleErrorResponse(0, "Vehicle Not Found"));
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<StaffResponse> handleStaffNotFoundException(StaffNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StaffErrorResponse(0, "Staff Not Found"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<VehicleResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new VehicleErrorResponse(0, e.getMessage()));
    }
}
