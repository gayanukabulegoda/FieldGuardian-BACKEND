package lk.ijse.fieldguardianbackend.controller;

import lk.ijse.fieldguardianbackend.customObj.FieldResponse;
import lk.ijse.fieldguardianbackend.customObj.impl.FieldErrorResponse;
import lk.ijse.fieldguardianbackend.dto.impl.FieldDTO;
import lk.ijse.fieldguardianbackend.exception.DataPersistFailedException;
import lk.ijse.fieldguardianbackend.exception.FieldNotFoundException;
import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import lk.ijse.fieldguardianbackend.service.FieldService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/field")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FieldController {
    private final FieldService fieldService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveField(@ModelAttribute FieldDTO fieldDTO) {
        fieldService.saveField(fieldDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateField(@PathVariable("id") String id, @ModelAttribute FieldDTO fieldDTO) {
        fieldService.updateField(id, fieldDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteField(@PathVariable("id") String id) {
        fieldService.deleteField(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FieldResponse> getField(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldById(id));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldDTO>> getAllFields() {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getAllFields());
    }

    @ExceptionHandler(FileConversionException.class)
    public ResponseEntity<FieldResponse> handleFileConversionException(FileConversionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new FieldErrorResponse(0, "File Conversion Failed"));
    }

    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<FieldResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new FieldErrorResponse(0, "Data Persist Failed"));
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<FieldResponse> handleFieldNotFoundException(FieldNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FieldErrorResponse(0, "Field Not Found"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<FieldResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new FieldErrorResponse(0, e.getMessage()));
    }
}