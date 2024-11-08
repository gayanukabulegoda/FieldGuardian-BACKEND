package lk.ijse.fieldguardianbackend.exception;

import lombok.Getter;

@Getter
public class DataPersistFailedException extends RuntimeException {
    private final int errorCode;
    public DataPersistFailedException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    public DataPersistFailedException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}