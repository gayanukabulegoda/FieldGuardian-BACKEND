package lk.ijse.fieldguardianbackend.exception.advice;

import lk.ijse.fieldguardianbackend.customObj.*;
import lk.ijse.fieldguardianbackend.customObj.impl.*;
import lk.ijse.fieldguardianbackend.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;
/**
 * This class is used to handle all the exceptions that are thrown in the application.
 * This class is annotated with @ControllerAdvice to indicate that this class is used to handle exceptions globally.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * This method is used to handle the exceptions that are thrown when the validation of the request body fails.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type MethodArgumentNotValidException.
     * This method returns a ResponseEntity object with a map of error messages and the status code.
     * @param ex MethodArgumentNotValidException object
     * @return ResponseEntity object with a map of error messages and the status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
    /**
     * This method is used to handle the exceptions that are thrown when the file conversion fails.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type FileConversionException.
     * This method returns a ResponseEntity object with a FileResponse object and the status code.
     * @param e FileConversionException object
     * @return ResponseEntity object with a FileResponse object and the status code
     */
    @ExceptionHandler(FileConversionException.class)
    public ResponseEntity<FileResponse> handleFileConversionException(FileConversionException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new FileErrorResponse(0, e.getMessage()));
    }
    /**
     * This method is used to handle the exceptions that are thrown when the data persist fails.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type DataPersistFailedException.
     * This method returns a ResponseEntity object with a DataPersistResponse object and the status code.
     * @param e DataPersistFailedException object
     * @return ResponseEntity object with a DataPersistResponse object and the status code
     */
    @ExceptionHandler(DataPersistFailedException.class)
    public ResponseEntity<DataPersistResponse> handleDataPersistFailedException(DataPersistFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new DataPersistErrorResponse(e.getErrorCode(), e.getMessage()));
    }
    /**
     * This method is used to handle the exceptions that are thrown when the staff is not found.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type StaffNotFoundException.
     * This method returns a ResponseEntity object with a StaffResponse object and the status code.
     * @param e StaffNotFoundException object
     * @return ResponseEntity object with a StaffResponse object and the status code
     */
    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<StaffResponse> handleStaffNotFoundException(StaffNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StaffErrorResponse(0, e.getMessage()));
    }
    /**
     * This method is used to handle the exceptions that are thrown when the field is not found.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type FieldNotFoundException.
     * This method returns a ResponseEntity object with a FieldResponse object and the status code.
     * @param e FieldNotFoundException object
     * @return ResponseEntity object with a FieldResponse object and the status code
     */
    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<FieldResponse> handleFieldNotFoundException(FieldNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new FieldErrorResponse(0, e.getMessage()));
    }
    /**
     * This method is used to handle the exceptions that are thrown when the crop is not found.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type CropNotFoundException.
     * This method returns a ResponseEntity object with a CropResponse object and the status code.
     * @param e CropNotFoundException object
     * @return ResponseEntity object with a CropResponse object and the status code
     */
    @ExceptionHandler(CropNotFoundException.class)
    public ResponseEntity<CropResponse> handleCropNotFoundException(CropNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new CropErrorResponse(0, e.getMessage()));
    }
    /**
     * This method is used to handle the exceptions that are thrown when the equipment is not found.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type EquipmentNotFoundException.
     * This method returns a ResponseEntity object with a EquipmentResponse object and the status code.
     * @param e EquipmentNotFoundException object
     * @return ResponseEntity object with a EquipmentResponse object and the status code
     */
    @ExceptionHandler(EquipmentNotFoundException.class)
    public ResponseEntity<EquipmentResponse> handleEquipmentNotFoundException(EquipmentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new EquipmentErrorResponse(0, e.getMessage()));
    }
    /**
     * This method is used to handle the exceptions that are thrown when the vehicle is not found.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type VehicleNotFoundException.
     * This method returns a ResponseEntity object with a VehicleResponse object and the status code.
     * @param e VehicleNotFoundException object
     * @return ResponseEntity object with a VehicleResponse object and the status code
     */
    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<VehicleResponse> handleVehicleNotFoundException(VehicleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new VehicleErrorResponse(0, e.getMessage()));
    }
    /**
     * This method is used to handle the exceptions that are thrown when the monitoring log is not found.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type MonitoringLogNotFoundException.
     * This method returns a ResponseEntity object with a MonitoringLogResponse object and the status code.
     * @param e MonitoringLogNotFoundException object
     * @return ResponseEntity object with a MonitoringLogResponse object and the status code
     */
    @ExceptionHandler(MonitoringLogNotFoundException.class)
    public ResponseEntity<MonitoringLogResponse> handleMonitoringLogNotFoundException(MonitoringLogNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MonitoringLogErrorResponse(0, e.getMessage()));
    }
    /**
     * This method is used to handle general exceptions that are not specifically handled by other methods.
     * This method is annotated with @ExceptionHandler to indicate that this method is used to handle exceptions to the type Exception.
     * This method returns a ResponseEntity object with an InternalServerResponse object and the status code.
     * @param e Exception object
     * @return ResponseEntity object with an InternalServerResponse object and the status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<InternalServerResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new InternalServerErrorResponse(0, e.getMessage()));
    }
}