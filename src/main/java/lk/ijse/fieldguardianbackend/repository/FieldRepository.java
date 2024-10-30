package lk.ijse.fieldguardianbackend.repository;

import lk.ijse.fieldguardianbackend.entity.impl.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends JpaRepository<Field, String> {
}
