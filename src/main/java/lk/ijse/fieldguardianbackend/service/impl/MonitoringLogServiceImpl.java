package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.impl.*;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.Crop;
import lk.ijse.fieldguardianbackend.entity.impl.MonitoringLog;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.exception.*;
import lk.ijse.fieldguardianbackend.repository.CropRepository;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.repository.MonitoringLogRepository;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.service.MonitoringLogService;
import lk.ijse.fieldguardianbackend.util.DataConversionUtil;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
/**
 * This class was created for the business logic of MonitoringLog
 * service implementation
 * @author - Gayanuka Bulegoda
 */
@Service
@RequiredArgsConstructor
public class MonitoringLogServiceImpl implements MonitoringLogService {
    private final MonitoringLogRepository monitoringLogRepository;
    private final FieldRepository fieldRepository;
    private final StaffRepository staffRepository;
    private final CropRepository cropRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveMonitoringLog(MonitoringLogSaveDTO monitoringLogSaveDTO) {
        monitoringLogSaveDTO.setCode(customIdGenerator.generateId(IdPrefix.MONITORING_LOG.getPrefix()));
        try {
            MonitoringLog monitoringLog = mapping.convertToEntity(monitoringLogSaveDTO, MonitoringLog.class);
            monitoringLog.setObservedImage(DataConversionUtil.toBase64(monitoringLogSaveDTO.getObservedImage()));
            monitoringLogRepository.save(monitoringLog);
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Monitoring Log", 0, e);
        }
    }

    @Override
    @Transactional
    public void updateMonitoringLog(String id, MonitoringLogSaveDTO monitoringLogSaveDTO) {
        MonitoringLog monitoringLog = monitoringLogRepository.findById(id)
                .orElseThrow(() -> new MonitoringLogNotFoundException("Monitoring log not found"));
        monitoringLog.setDate(monitoringLogSaveDTO.getDate());
        monitoringLog.setDetails(monitoringLogSaveDTO.getDetails());
        monitoringLog.setField(fieldRepository.findById(monitoringLogSaveDTO.getFieldCode())
                .orElseThrow(() -> new FieldNotFoundException("Field not found")));
        try {
            monitoringLog.setObservedImage(DataConversionUtil.toBase64(monitoringLogSaveDTO.getObservedImage()));
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        }
    }

    @Override
    public void updateMonitoringLogStaffAndCrops(UpdateMonitoringLogStaffAndCropsDTO updateDTO) {
        MonitoringLog monitoringLog = monitoringLogRepository.findById(updateDTO.getMonitoringLogId())
                .orElseThrow(() -> new MonitoringLogNotFoundException("Monitoring log not found"));
        try {
            List<Staff> staffList = staffRepository.findAllById(updateDTO.getStaffIds());
            if (staffList.size() != updateDTO.getStaffIds().size())
                throw new StaffNotFoundException("One or more staff IDs are invalid");
            List<Crop> cropList = cropRepository.findAllById(updateDTO.getCropCodes());
            if (cropList.size() != updateDTO.getCropCodes().size())
                throw new CropNotFoundException("One or more crop codes are invalid");
            monitoringLog.setStaff(staffList);
            monitoringLog.setCrops(cropList);
            monitoringLogRepository.save(monitoringLog);
        } catch (CropNotFoundException | StaffNotFoundException e) {
            if (monitoringLog.getStaff().isEmpty() && monitoringLog.getCrops().isEmpty())
                monitoringLogRepository.deleteById(monitoringLog.getCode());
            if (e instanceof CropNotFoundException) throw new CropNotFoundException(e.getMessage());
            else throw new StaffNotFoundException(e.getMessage());
        }
    }

    @Override
    public MonitoringLogResponse getSelectedMonitoringLog(String id) {
        MonitoringLog monitoringLog = monitoringLogRepository.findById(id)
                .orElseThrow(() -> new MonitoringLogNotFoundException("Monitoring log not found"));
        return mapping.convertToDTO(monitoringLog, MonitoringLogResponseDTO.class);
    }

    @Override
    public List<MonitoringLogResponseDTO> getAllMonitoringLogs() {
        List<MonitoringLog> monitoringLogs = monitoringLogRepository.findAll(Sort.by(Sort.Direction.DESC, "code"));
        if (monitoringLogs.isEmpty()) throw new MonitoringLogNotFoundException("No monitoring logs found");
        List<MonitoringLogResponseDTO> monitoringLogResponseDTOS =
                mapping.convertToDTOList(monitoringLogs, MonitoringLogResponseDTO.class);
        for (MonitoringLogResponseDTO monitoringLogResponseDTO : monitoringLogResponseDTOS) {
            monitoringLogResponseDTO.setStaffCount(
                    monitoringLogRepository.getStaffCountByMonitoringLogId(monitoringLogResponseDTO.getCode()));
        }
        return monitoringLogResponseDTOS;
    }

    @Override
    public List<CropResponseDTO> getCropsByMonitoringLogId(String monitoringLogId) {
        List<Crop> crops = monitoringLogRepository.findCropsByMonitoringLogId(monitoringLogId);
        if (crops.isEmpty()) throw new MonitoringLogNotFoundException("No crops found for the given monitoring log ID");
        return mapping.convertToDTOList(crops, CropResponseDTO.class);
    }

    @Override
    public List<StaffDTO> getStaffByMonitoringLogId(String monitoringLogId) {
        List<Staff> staff = monitoringLogRepository.findStaffByMonitoringLogId(monitoringLogId);
        if (staff.isEmpty()) throw new MonitoringLogNotFoundException("No staff found for the given monitoring log ID");
        return mapping.convertToDTOList(staff, StaffDTO.class);
    }

    @Override
    public List<MonitoringLogResponseDTO> filterMonitoringLogs(MonitoringLogFilterDTO filterDTO) {
        String nameFilter = filterDTO.getName() != null ? filterDTO.getName() : null;
        LocalDate startOfDay = filterDTO.getDate() != null ? filterDTO.getDate() : null;
        LocalDate endOfDay = filterDTO.getDate() != null ? filterDTO.getDate() : null;
        return monitoringLogRepository.findAllByFilters(
                        nameFilter,
                        startOfDay,
                        endOfDay
                ).stream()
                .map(monitoringLog -> mapping.convertToDTO(monitoringLog, MonitoringLogResponseDTO.class)).toList();
    }
}