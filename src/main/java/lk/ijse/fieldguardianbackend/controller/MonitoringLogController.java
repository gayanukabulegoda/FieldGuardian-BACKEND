package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.MonitoringLogErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.MonitoringLogDTO;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import lk.ijse.fieldguardianbackend.exception.MonitoringLogNotFoundException;
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
    public ResponseEntity<Void> saveMonitoringLog(@ModelAttribute MonitoringLogDTO monitoringLogDTO) {
        monitoringLogService.saveMonitoringLog(monitoringLogDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMonitoringLog(@PathVariable("id") String id, @ModelAttribute MonitoringLogDTO monitoringLogDTO) {
        monitoringLogService.updateMonitoringLog(id, monitoringLogDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteMonitoringLog(@PathVariable("id") String id) {
        monitoringLogService.deleteMonitoringLog(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonitoringLogResponse> getMonitoringLog(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(monitoringLogService.getSelectedMonitoringLog(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonitoringLogDTO>> getAllMonitoringLogs() {
        return ResponseEntity.status(HttpStatus.OK).body(monitoringLogService.getAllMonitoringLogs());
    }

    @ExceptionHandler(FileConversionException.class)
    public ResponseEntity<MonitoringLogResponse> handleFileConversionException(FileConversionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MonitoringLogErrorResponse(0, "File Conversion Failed"));
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<MonitoringLogResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MonitoringLogErrorResponse(0, "Data Persist Failed"));
    }

    @ExceptionHandler(MonitoringLogNotFoundException.class)
    public ResponseEntity<MonitoringLogResponse> handleMonitoringLogNotFoundException(MonitoringLogNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MonitoringLogErrorResponse(0, "Monitoring Log Not Found"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MonitoringLogResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MonitoringLogErrorResponse(0, e.getMessage()));
    }
}