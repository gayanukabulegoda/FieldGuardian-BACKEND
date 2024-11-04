package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.MonitoringLogResponse;
import lk.ijse.fieldguardianbackend.dto.impl.MonitoringLogDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.impl.MonitoringLog;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import lk.ijse.fieldguardianbackend.exception.MonitoringLogNotFoundException;
import lk.ijse.fieldguardianbackend.repository.CropRepository;
import lk.ijse.fieldguardianbackend.repository.FieldRepository;
import lk.ijse.fieldguardianbackend.repository.MonitoringLogRepository;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.service.MonitoringLogService;
import lk.ijse.fieldguardianbackend.util.DataConversionUtil;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        monitoringLog.setStaff(staffRepository.findAllById(monitoringLogDTO.getStaffIds()));
        monitoringLog.setCrops(cropRepository.findAllById(monitoringLogDTO.getCropCodes()));
        try {
            monitoringLog.setObservedImage(DataConversionUtil.toBase64(monitoringLogDTO.getObservedImage()));
            monitoringLogRepository.save(monitoringLog);
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Monitoring Log", 0, e);
        }
    }

    @Override
    @Transactional
    public void updateMonitoringLog(String id, MonitoringLogDTO monitoringLogDTO) {
        MonitoringLog monitoringLog = monitoringLogRepository.findById(id)
                .orElseThrow(() -> new MonitoringLogNotFoundException("Monitoring log not found"));
        monitoringLog.setDate(monitoringLogDTO.getDate());
        monitoringLog.setDetails(monitoringLogDTO.getDetails());
        try {
            monitoringLog.setObservedImage(DataConversionUtil.toBase64(monitoringLogDTO.getObservedImage()));
        } catch (FileConversionException e) {
            throw new FileConversionException("Cannot convert image to base64", e);
        }
        monitoringLog.setStaff(staffRepository.findAllById(monitoringLogDTO.getStaffIds()));
        monitoringLog.setCrops(cropRepository.findAllById(monitoringLogDTO.getCropCodes()));
    }

    @Override
    public void deleteMonitoringLog(String id) {
        if (!monitoringLogRepository.existsById(id))
            throw new MonitoringLogNotFoundException("Monitoring log not found");
        monitoringLogRepository.deleteById(id);
    }

    @Override
    public MonitoringLogResponse getSelectedMonitoringLog(String id) {
        MonitoringLog monitoringLog = monitoringLogRepository.findById(id)
                .orElseThrow(() -> new MonitoringLogNotFoundException("Monitoring log not found"));
        return mapping.convertToDTO(monitoringLog, MonitoringLogDTO.class);
    }

    @Override
    public List<MonitoringLogDTO> getAllMonitoringLogs() {
        List<MonitoringLog> monitoringLogs = monitoringLogRepository.findAll();
        if (monitoringLogs.isEmpty()) throw new MonitoringLogNotFoundException("No monitoring logs found");
        return mapping.convertToDTOList(monitoringLogs, MonitoringLogDTO.class);
    }
}
