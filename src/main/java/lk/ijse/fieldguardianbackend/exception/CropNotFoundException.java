package lk.ijse.fieldguardianbackend.exception;

public class CropNotFoundException extends RuntimeException {
    public CropNotFoundException(String message) {
        super(message);
    }
}