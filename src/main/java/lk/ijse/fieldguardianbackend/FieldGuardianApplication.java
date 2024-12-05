/**
 * FieldGuardianApplication.java
 * ----------------------------------------------
 * Main class for the Field Guardian Backend Application.
 * ----------------------------------------------
 * This class serves as the entry point for the Spring Boot application.
 * It also defines a `ModelMapper` bean for object mapping.
 *
 * @author Gayanuka Bulegoda
 * @repository https://github.com/gayanukabulegoda/FieldGuardian-BACKEND.git
 * @created 29 Oct 2024
 */
package lk.ijse.fieldguardianbackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FieldGuardianApplication {
    public static void main(String[] args) {
        SpringApplication.run(FieldGuardianApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}