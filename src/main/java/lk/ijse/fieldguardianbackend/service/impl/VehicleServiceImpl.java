package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.enums.VehicleStatus;
import lk.ijse.fieldguardianbackend.entity.impl.Vehicle;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.exception.VehicleNotFoundException;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.repository.VehicleRepository;
import lk.ijse.fieldguardianbackend.service.VehicleService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final StaffRepository staffRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        vehicleDTO.setCode(customIdGenerator.generateId(IdPrefix.VEHICLE.getPrefix()));
        Vehicle vehicle = mapping.convertToEntity(vehicleDTO, Vehicle.class);
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        try {
            vehicleRepository.save(vehicle);
        } catch (Exception e) {
            log.error("Error saving vehicle: {}", e.getMessage(), e);
            throw new DataPersistFailedException("Cannot Save Vehicle", e);
        }
    }

    @Override
    @Transactional
    public void updateVehicle(String id, VehicleDTO vehicleDTO) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        vehicle.setLicensePlateNumber(vehicleDTO.getLicensePlateNumber());
        vehicle.setCategory(vehicleDTO.getCategory());
        vehicle.setFuelType(vehicleDTO.getFuelType());
        vehicle.setRemark(vehicleDTO.getRemark());
        vehicle.setDriver(staffRepository.findById(vehicleDTO.getDriverId())
                .orElseThrow(() -> new StaffNotFoundException("Driver not found")));
    }

    @Override
    public void deleteVehicle(String id) {
        if (!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException("Vehicle not found");
        vehicleRepository.deleteById(id);
    }

    @Override
    public VehicleResponse getSelectedVehicle(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        return mapping.convertToDTO(vehicle, VehicleDTO.class);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        if (vehicles.isEmpty()) throw new VehicleNotFoundException("No vehicles found");
        return mapping.convertToDTOList(vehicles, VehicleDTO.class);
    }
}
