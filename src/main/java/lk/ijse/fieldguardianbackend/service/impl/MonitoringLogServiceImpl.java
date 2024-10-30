package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.MonitoringLogErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.MonitoringLogDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.MonitoringLog;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.MonitoringLogNotFoundException;
import lk.ijse.fieldguardianbackend.repository.CropRepository;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.repository.MonitoringLogRepository;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.service.MonitoringLogService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public void saveMonitoringLog(MonitoringLogDTO monitoringLogDTO) {
        monitoringLogDTO.setCode(customIdGenerator.generateId(IdPrefix.MONITORING_LOG.getPrefix()));
        MonitoringLog monitoringLog = mapping.convertToEntity(monitoringLogDTO, MonitoringLog.class);
        monitoringLog.setFields(fieldRepository.findAllById(monitoringLogDTO.getFieldCodes()));
        monitoringLog.setStaff(staffRepository.findAllById(monitoringLogDTO.getStaffIds()));
        monitoringLog.setCrops(cropRepository.findAllById(monitoringLogDTO.getCropCodes()));
        try {
            monitoringLogRepository.save(monitoringLog);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Monitoring Log", e);
        }
    }

    @Override
    @Transactional
    public void updateMonitoringLog(String id, MonitoringLogDTO monitoringLogDTO) {
        MonitoringLog monitoringLog = monitoringLogRepository.findById(id)
                .orElseThrow(() -> new MonitoringLogNotFoundException("Monitoring log not found"));
        monitoringLog.setDate(monitoringLogDTO.getDate());
        monitoringLog.setDetails(monitoringLogDTO.getDetails());
        monitoringLog.setObservedImage(monitoringLogDTO.getObservedImage());
        monitoringLog.setFields(fieldRepository.findAllById(monitoringLogDTO.getFieldCodes()));
        monitoringLog.setStaff(staffRepository.findAllById(monitoringLogDTO.getStaffIds()));
        monitoringLog.setCrops(cropRepository.findAllById(monitoringLogDTO.getCropCodes()));
    }

    @Override
    public void deleteMonitoringLog(String id) {
        if (!monitoringLogRepository.existsById(id)) {
            throw new MonitoringLogNotFoundException("Monitoring log not found");
        }
        monitoringLogRepository.deleteById(id);
    }

    @Override
    public MonitoringLogResponse getSelectedMonitoringLog(String id) {
        Optional<MonitoringLog> byId = monitoringLogRepository.findById(id);
        return (byId.isPresent())
                ? mapping.convertToDTO(byId.get(), MonitoringLogDTO.class)
                : new MonitoringLogErrorResponse(0, "Monitoring log not found");
    }

    @Override
    public List<MonitoringLogDTO> getAllMonitoringLogs() {
        return mapping.convertToDTOList(monitoringLogRepository.findAll(), MonitoringLogDTO.class);
    }
}
