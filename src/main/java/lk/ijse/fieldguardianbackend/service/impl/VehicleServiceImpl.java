package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.enums.VehicleStatus;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final StaffRepository staffRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveVehicle(VehicleDTO vehicleDTO) {
        if (vehicleRepository.findByLicensePlateNumber(vehicleDTO.getLicensePlateNumber()))
            throw new DataPersistFailedException("License Plate Number already exists", 1);
        vehicleDTO.setCode(customIdGenerator.generateId(IdPrefix.VEHICLE.getPrefix()));
        Vehicle vehicle = mapping.convertToEntity(vehicleDTO, Vehicle.class);
        vehicle.setStatus(VehicleStatus.AVAILABLE);
        try {
            vehicleRepository.save(vehicle);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Vehicle", 0, e);
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
        if (VehicleStatus.OUT_OF_SERVICE.name().equals(vehicleDTO.getStatus())) {
            vehicle.setStatus(VehicleStatus.OUT_OF_SERVICE);
            vehicle.setDriver(null);
        }
    }

    @Override
    @Transactional
    public void updateVehicleDriver(String vehicleId, String driverId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        Staff driver = staffRepository.findById(driverId)
                .orElseThrow(() -> new StaffNotFoundException("Driver not found"));
        vehicle.setDriver(driver);
        vehicle.setStatus(VehicleStatus.IN_USE);
    }

    @Override
    @Transactional
    public void deleteVehicle(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        vehicle.setStatus(VehicleStatus.REMOVED);
    }

    @Override
    public VehicleResponse getSelectedVehicle(String id) {
        Vehicle vehicle = vehicleRepository.findByIdAndStatusNot(id, VehicleStatus.REMOVED)
                .orElseThrow(() -> new VehicleNotFoundException("Vehicle not found"));
        return mapping.convertToDTO(vehicle, VehicleDTO.class);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAllByStatusNot(VehicleStatus.REMOVED);
        if (vehicles.isEmpty()) throw new VehicleNotFoundException("No vehicles found");
        return mapping.convertToDTOList(vehicles, VehicleDTO.class);
    }
}