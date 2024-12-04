package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.dto.impl.FieldMonitoringCountDTO;
import lk.ijse.fieldguardianbackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * REST controller for managing dashboard.
 */
@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;
    /**
     * {@code GET /user-count} : Get the total number of users.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the total number of users in the body.
     */
    @GetMapping("/user-count")
    public ResponseEntity<Long> getTotalUsers() {
        return ResponseEntity.ok(dashboardService.getTotalUsers());
    }
    /**
     * {@code GET /staff-count} : Get the total number of active staff.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the total number of active staff in the body.
     */
    @GetMapping("/staff-count")
    public ResponseEntity<Long> getTotalActiveStaff() {
        return ResponseEntity.ok(dashboardService.getTotalActiveStaff());
    }
    /**
     * {@code GET /crop-count} : Get the total number of active crops.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the total number of active crops in the body.
     */
    @GetMapping("/crop-count")
    public ResponseEntity<Long> getTotalActiveCrops() {
        return ResponseEntity.ok(dashboardService.getTotalActiveCrops());
    }
    /**
     * {@code GET /vehicle-count} : Get the total number of active vehicles.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the total number of active vehicles in the body.
     */
    @GetMapping("/vehicle-count")
    public ResponseEntity<Long> getTotalActiveVehicles() {
        return ResponseEntity.ok(dashboardService.getTotalActiveVehicles());
    }
    /**
     * {@code GET /equipment-count} : Get the total number of active equipment.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the total number of active equipment in the body.
     */
    @GetMapping("/equipment-count")
    public ResponseEntity<Long> getTotalActiveEquipment() {
        return ResponseEntity.ok(dashboardService.getTotalActiveEquipment());
    }
    /**
     * {@code GET /top-fields} : Get the top five monitored fields.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the top five monitored fields in the body.
     */
    @GetMapping("/top-fields")
    public List<FieldMonitoringCountDTO> getTopMonitoredFields() {
        return dashboardService.getTopFiveMonitoredFields();
    }
}