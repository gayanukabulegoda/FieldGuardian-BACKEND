package lk.ijse.fieldguardianbackend.util;

import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import org.springframework.data.geo.Point;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class DataConversionUtil {
    public static String toBase64(MultipartFile file) {
        try {
            byte[] fileByteCollection = file.getBytes();
            return Base64.getEncoder().encodeToString(fileByteCollection);
        } catch (Exception e) {
            throw new FileConversionException("Failed to convert file to base64", e);
        }
    }

    public static Point toPoint(String pointString) {
        if (pointString == null || pointString.isEmpty())
            throw new FileConversionException("Point string cannot be null or empty");
        String[] parts = pointString.split(",");
        if (parts.length != 2)
            throw new FileConversionException("Point string must be in the format 'latitude,longitude'");
        try {
            double latitude = Double.parseDouble(parts[0].trim());
            double longitude = Double.parseDouble(parts[1].trim());
            return new Point(latitude, longitude);
        } catch (NumberFormatException e) {
            throw new FileConversionException("Invalid latitude or longitude value", e);
        }
    }
}
