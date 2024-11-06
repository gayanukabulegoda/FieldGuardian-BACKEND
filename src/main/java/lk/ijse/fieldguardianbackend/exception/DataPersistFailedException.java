package lk.ijse.fieldguardianbackend.exception;

import lombok.Getter;

@Getter
public class DataPersistFailedException extends RuntimeException {
    private final int errorCode;
//    public DataPersistFailedException(String message) {
//        super(message);
//        this.errorCode = 1; // Error code for license plate number already exists
//    }
//    public DataPersistFailedException(String message, Throwable cause) {
//        super(message, cause);
//        this.errorCode = 0; // Error code for general data persist failure
//    }
    public DataPersistFailedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public DataPersistFailedException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}