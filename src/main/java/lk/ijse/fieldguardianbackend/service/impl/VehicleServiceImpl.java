package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.VehicleResponse;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleFilterDTO;
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
/**
 * This class was created for the business logic of Vehicle
 * service implementation
 * @author - Gayanuka Bulegoda
 */
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
        vehicle.setStatus(VehicleStatus.valueOf(vehicleDTO.getStatus()));
        if (vehicle.getStatus() == VehicleStatus.OUT_OF_SERVICE || vehicle.getStatus() == VehicleStatus.AVAILABLE)
            vehicle.setDriver(null);
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

    @Override
    public List<VehicleDTO> filterVehicle(VehicleFilterDTO filterDTO) {
        VehicleStatus status = (filterDTO.getStatus() != null) ? VehicleStatus.valueOf(filterDTO.getStatus()) : null;
        return vehicleRepository.findAllByFilters(
                        filterDTO.getLicensePlateNumber(),
                        filterDTO.getCategory(),
                        status
                )
                .stream()
                .filter(vehicle -> !vehicle.getStatus().equals(VehicleStatus.REMOVED))
                .map(vehicle -> mapping.convertToDTO(vehicle, VehicleDTO.class)).toList();
    }
}