package lk.ijse.fieldguardianbackend.service.impl;

import lk.ijse.fieldguardianbackend.customObj.StaffResponse;
import lk.ijse.fieldguardianbackend.dto.impl.StaffDTO;
import lk.ijse.fieldguardianbackend.entity.enums.Designation;
import lk.ijse.fieldguardianbackend.entity.enums.Gender;
import lk.ijse.fieldguardianbackend.entity.enums.IdPrefix;
import lk.ijse.fieldguardianbackend.entity.enums.StaffStatus;
import lk.ijse.fieldguardianbackend.entity.impl.Staff;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.StaffNotFoundException;
import lk.ijse.fieldguardianbackend.repository.StaffRepository;
import lk.ijse.fieldguardianbackend.service.StaffService;
import lk.ijse.fieldguardianbackend.util.CustomIdGenerator;
import lk.ijse.fieldguardianbackend.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final Mapping mapping;
    private final CustomIdGenerator customIdGenerator;

    @Override
    public void saveStaff(StaffDTO staffDTO) {
        staffDTO.setId(customIdGenerator.generateId(IdPrefix.STAFF.getPrefix()));
        Staff staff = mapping.convertToEntity(staffDTO, Staff.class);
        staff.setDesignation(Designation.fromString(staffDTO.getDesignation()));
        staff.setRole(staff.getDesignation().getRole());
        staff.setGender(Gender.fromString(staffDTO.getGender()));
        staff.setStatus(StaffStatus.ACTIVE);
        try {
            staffRepository.save(staff);
        } catch (Exception e) {
            throw new DataPersistFailedException("Cannot Save Staff", e);
        }
    }

    @Override
    @Transactional
    public void updateStaff(String id, StaffDTO staffDTO) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
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
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        staff.setStatus(StaffStatus.REMOVED);
    }

    @Override
    public StaffResponse getSelectedStaff(String id) {
        Staff staff = staffRepository.findActiveStaffById(id)
                .orElseThrow(() -> new StaffNotFoundException("Staff not found"));
        return mapping.convertToDTO(staff, StaffDTO.class);
    }

    @Override
    public List<StaffDTO> getAllStaffs() {
        List<Staff> activeStaff = staffRepository.findAllActiveStaff();
        if (activeStaff.isEmpty()) throw new StaffNotFoundException("No staff found");
        return mapping.convertToDTOList(activeStaff, StaffDTO.class);
    }
}
