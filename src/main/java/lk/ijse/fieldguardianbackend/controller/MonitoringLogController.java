package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.CropResponse;
import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.CropErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.MonitoringLogErrorResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.StaffErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import lk.ijse.fieldguardianbackend.exception.*;
import lk.ijse.fieldguardianbackend.service.MonitoringLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monitoring-log")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MonitoringLogController {
    private final MonitoringLogService monitoringLogService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveMonitoringLog(@ModelAttribute MonitoringLogSaveDTO monitoringLogSaveDTO) {
        monitoringLogService.saveMonitoringLog(monitoringLogSaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMonitoringLog(@PathVariable("id") String id, @ModelAttribute MonitoringLogSaveDTO monitoringLogSaveDTO) {
        monitoringLogService.updateMonitoringLog(id, monitoringLogSaveDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping(value = "/update-staff-and-crops", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMonitoringLogStaffAndCrops(@RequestBody UpdateMonitoringLogStaffAndCropsDTO updateDTO) {
        monitoringLogService.updateMonitoringLogStaffAndCrops(updateDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonitoringLogResponse> getMonitoringLog(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(monitoringLogService.getSelectedMonitoringLog(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonitoringLogResponseDTO>> getAllMonitoringLogs() {
        return ResponseEntity.status(HttpStatus.OK).body(monitoringLogService.getAllMonitoringLogs());
    }

    @GetMapping("/{id}/crops")
    public ResponseEntity<List<CropResponseDTO>> getCropsByMonitoringLogId(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(monitoringLogService.getCropsByMonitoringLogId(id));
    }

    @GetMapping("/{id}/staff")
    public ResponseEntity<List<StaffDTO>> getStaffByMonitoringLogId(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(monitoringLogService.getStaffByMonitoringLogId(id));
    }

    @ExceptionHandler(FileConversionException.class)
    public ResponseEntity<MonitoringLogResponse> handleFileConversionException(FileConversionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MonitoringLogErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<MonitoringLogResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MonitoringLogErrorResponse(e.getErrorCode(), e.getMessage()));
    }

    @ExceptionHandler(MonitoringLogNotFoundException.class)
    public ResponseEntity<MonitoringLogResponse> handleMonitoringLogNotFoundException(MonitoringLogNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MonitoringLogErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<StaffResponse> handleStaffNotFoundException(StaffNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StaffErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(CropNotFoundException.class)
    public ResponseEntity<CropResponse> handleCropNotFoundException(CropNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CropErrorResponse(0, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MonitoringLogResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MonitoringLogErrorResponse(0, e.getMessage()));
    }
}