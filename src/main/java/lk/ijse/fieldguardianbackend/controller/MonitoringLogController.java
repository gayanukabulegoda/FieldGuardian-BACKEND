package lk.ijse.fieldguardianbackend.controller;

import jakarta.validation.Valid;
import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import lk.ijse.fieldguardianbackend.service.MonitoringLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * REST controller for managing Monitoring Logs.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/monitoring-log")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MonitoringLogController {
    private final MonitoringLogService monitoringLogService;
    /**
     * {@code POST /} : Create a new monitoring log.
     *
     * @param monitoringLogSaveDTO the monitoring log to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)}.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveMonitoringLog(@Valid @ModelAttribute MonitoringLogSaveDTO monitoringLogSaveDTO) {
        log.info("Request to save monitoring log: {}", monitoringLogSaveDTO);
        monitoringLogService.saveMonitoringLog(monitoringLogSaveDTO);
        log.info("Monitoring log saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /**
     * {@code PATCH /{id}} : Updates an existing monitoring log.
     *
     * @param id the id of the monitoring log to update.
     * @param monitoringLogSaveDTO the monitoring log to update.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateMonitoringLog(@PathVariable("id") String id, @Valid @ModelAttribute MonitoringLogSaveDTO monitoringLogSaveDTO) {
        log.info("Request to update monitoring log with id: {}", id);
        monitoringLogService.updateMonitoringLog(id, monitoringLogSaveDTO);
        log.info("Monitoring log with id: {} updated successfully", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code PATCH /update-staff-and-crops} : Updates staff and crops for an existing monitoring log.
     *
     * @param updateDTO the DTO containing staff and crop updates.
     * @return the {@link ResponseEntity} with status {@code 204 (No Content)}.
     */
    @PatchMapping(value = "/update-staff-and-crops", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMonitoringLogStaffAndCrops(@Valid @RequestBody UpdateMonitoringLogStaffAndCropsDTO updateDTO) {
        log.info("Request to update staff and crops for monitoring log: {}", updateDTO);
        monitoringLogService.updateMonitoringLogStaffAndCrops(updateDTO);
        log.info("Staff and crops for monitoring log updated successfully");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    /**
     * {@code GET /{id}} : Get the "id" monitoring log.
     *
     * @param id the id of the monitoring log to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monitoring log.
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonitoringLogResponse> getMonitoringLog(@PathVariable("id") String id) {
        log.info("Request to get monitoring log with id: {}", id);
        MonitoringLogResponse response = monitoringLogService.getSelectedMonitoringLog(id);
        log.info("Monitoring log with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * {@code GET /} : Get all monitoring logs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monitoring logs in body.
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonitoringLogResponseDTO>> getAllMonitoringLogs() {
        log.info("Request to get all monitoring logs");
        List<MonitoringLogResponseDTO> response = monitoringLogService.getAllMonitoringLogs();
        log.info("All monitoring logs retrieved successfully");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * {@code GET /{id}/crops} : Get crops by monitoring log id.
     *
     * @param id the id of the monitoring log.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crops in body.
     */
    @GetMapping("/{id}/crops")
    public ResponseEntity<List<CropResponseDTO>> getCropsByMonitoringLogId(@PathVariable String id) {
        log.info("Request to get crops for monitoring log with id: {}", id);
        List<CropResponseDTO> response = monitoringLogService.getCropsByMonitoringLogId(id);
        log.info("Crops for monitoring log with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    /**
     * {@code GET /{id}/staff} : Get staff by monitoring log id.
     *
     * @param id the id of the monitoring log.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of staff in body.
     */
    @GetMapping("/{id}/staff")
    public ResponseEntity<List<StaffDTO>> getStaffByMonitoringLogId(@PathVariable String id) {
        log.info("Request to get staff for monitoring log with id: {}", id);
        List<StaffDTO> response = monitoringLogService.getStaffByMonitoringLogId(id);
        log.info("Staff for monitoring log with id: {} retrieved successfully", id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}