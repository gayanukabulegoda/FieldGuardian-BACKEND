package lk.ijse.fieldguardianbackend.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

public class FileConversionUtil {
    public static String toBase64(MultipartFile file) {
        try {
            byte[] fileByteCollection = file.getBytes();
            return Base64.getEncoder().encodeToString(fileByteCollection);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
