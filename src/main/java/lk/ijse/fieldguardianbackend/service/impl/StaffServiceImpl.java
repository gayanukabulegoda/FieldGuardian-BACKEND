package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffFieldDTO;
import lk.ijse.fieldguardianbackend.dto.impl.StaffFilterDTO;
import lk.ijse.fieldguardianbackend.dto.impl.VehicleDTO;
import lk.ijse.fieldguardianbackend.entity.enums.Designation;
import lk.ijse.fieldguardianbackend.entity.enums.Gender;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.enums.Status;
import lk.ijse.fieldguardianbackend.entity.impl.Field;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.entity.impl.Vehicle;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.exception.VehicleNotFoundException;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.repository.UserRepository;
import lk.ijse.fieldguardianbackend.service.StaffService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
/**
 * This class was created for the business logic of Staff
 * service implementation
 * @author - Gayanuka Bulegoda
 */
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveStaff(StaffDTO staffDTO) {
        if (staffRepository.existsByContactNo(staffDTO.getContactNo()))
            throw new DataPersistFailedException("Contact number already exists", 1);
        if (staffRepository.existsByEmail(staffDTO.getEmail()))
            throw new DataPersistFailedException("Email already exists", 2);
        staffDTO.setId(customIdGenerator.generateId(IdPrefix.STAFF.getPrefix()));
        Staff staff = mapping.convertToEntity(staffDTO, Staff.class);
        staff.setDesignation(Designation.fromString(staffDTO.getDesignation()));
        staff.setRole(staff.getDesignation().getRole());
        staff.setGender(Gender.fromString(staffDTO.getGender()));
        staff.setStatus(Status.ACTIVE);
        try {
            staffRepository.save(staff);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Staff", 0, e);
        }
    }

    @Override
    @Transactional
    public void updateStaff(String id, StaffDTO staffDTO) {
        Staff staff = staffRepository.findActiveStaffById(id, Status.ACTIVE)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        if (!staff.getContactNo().equals(staffDTO.getContactNo()) && staffRepository.existsByContactNo(staffDTO.getContactNo()))
            throw new DataPersistFailedException("Contact number already exists", 1);
        if (!staff.getEmail().equals(staffDTO.getEmail()) && staffRepository.existsByEmail(staffDTO.getEmail()))
            throw new DataPersistFailedException("Email already exists", 2);
        staff.setFirstName(staffDTO.getFirstName());
        staff.setLastName(staffDTO.getLastName());
        staff.setDesignation(Designation.fromString(staffDTO.getDesignation()));
        staff.setRole(staff.getDesignation().getRole());
        staff.setGender(Gender.fromString(staffDTO.getGender()));
        staff.setJoinedDate(staffDTO.getJoinedDate());
        staff.setDateOfBirth(staffDTO.getDateOfBirth());
        staff.setAddress(staffDTO.getAddress());
        staff.setPostalCode(staffDTO.getPostalCode());
        staff.setContactNo(staffDTO.getContactNo());
        staff.setEmail(staffDTO.getEmail());
    }

    @Override
    @Transactional
    public void deleteStaff(String id) {
        Staff staff = staffRepository.findActiveStaffById(id, Status.ACTIVE)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        if (userRepository.existsByEmail(staff.getEmail()))
            userServiceImpl.deleteUser(staff.getEmail());
        staff.setStatus(Status.REMOVED);
    }

    @Override
    public StaffResponse getSelectedStaff(String id) {
        Staff staff = staffRepository.findActiveStaffById(id, Status.ACTIVE)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        return mapping.convertToDTO(staff, StaffDTO.class);
    }

    @Override
    public List<StaffDTO> getAllStaffs() {
        List<Staff> activeStaff = staffRepository.findAllActiveStaff(Status.ACTIVE);
        if (activeStaff.isEmpty()) throw new StaffNotFoundException("No staff found");
        return mapping.convertToDTOList(activeStaff, StaffDTO.class);
    }

    @Override
    public List<VehicleDTO> getStaffVehicles(String staffId) {
        List<Vehicle> vehicles = staffRepository.findVehiclesByStaffId(staffId);
        if (vehicles.isEmpty()) throw new VehicleNotFoundException("No vehicles found for staff");
        return mapping.convertToDTOList(vehicles, VehicleDTO.class);
    }

    @Override
    public List<StaffFieldDTO> getStaffFields(String staffId) {
        List<Field> fields = staffRepository.findFieldsByStaffId(staffId);
        if (fields.isEmpty()) throw new FieldNotFoundException("No fields found for staff");
        return mapping.convertToDTOList(fields, StaffFieldDTO.class);
    }

    @Override
    public List<StaffDTO> getStaffWithoutEquipment() {
        List<Staff> staffList = staffRepository.findAllActiveStaffWithoutEquipment(Status.ACTIVE);
        if (staffList.isEmpty()) throw new StaffNotFoundException("No staff found without equipment");
        return mapping.convertToDTOList(staffList, StaffDTO.class);
    }

    @Override
    public List<StaffDTO> filterStaff(StaffFilterDTO filterDTO) {
        Designation enumDesignation = filterDTO.getDesignation() != null
                ? Designation.valueOf(filterDTO.getDesignation().toUpperCase()) : null;
        Gender enumGender = filterDTO.getGender() != null ? Gender.valueOf(filterDTO.getGender().toUpperCase()) : null;
        List<Staff> filteredStaff = staffRepository.findAllByFilters(
                filterDTO.getName(),
                enumDesignation,
                enumGender
        );
        return mapping.convertToDTOList(filteredStaff, StaffDTO.class);
    }

    @Override
    public List<Designation> getAllStaffDesignations() {
        return Arrays.asList(Designation.values());
    }
}