package lk.ijse.fieldguardianbackend.entity.impl;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lk.ijse.fieldguardianbackend.entity.SuperEntity;
import lk.ijse.fieldguardianbackend.entity.enums.Designation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User implements SuperEntity {
    @Id
    private String email;
    @Column(nullable = false, length = 50)
    private String password;
    @Column(nullable = false, length = 15)
    private String role;

    public void setRoleFromDesignation(Designation designation) {
        this.role = designation.getRole();
    }
}