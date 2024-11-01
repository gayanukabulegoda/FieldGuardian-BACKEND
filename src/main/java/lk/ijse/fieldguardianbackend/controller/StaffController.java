package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.StaffErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StaffController {
    private final StaffService staffService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveStaff(@RequestBody StaffDTO staffDTO) {
        staffService.saveStaff(staffDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateStaff(@PathVariable("id") String id, @RequestBody StaffDTO staffDTO) {
        staffService.updateStaff(id, staffDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable("id") String id) {
        staffService.deleteStaff(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StaffResponse> getStaff(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getSelectedStaff(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StaffDTO>> getAllStaff() {
        return ResponseEntity.status(HttpStatus.OK).body(staffService.getAllStaffs());
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<StaffResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StaffErrorResponse(0, "Data Persist Failed"));
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<StaffResponse> handleStaffNotFoundException(StaffNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StaffErrorResponse(0, "Staff Not Found"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StaffResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StaffErrorResponse(0, e.getMessage()));
    }
}