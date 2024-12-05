package lk.ijse.fieldguardianbackend.util;

import lk.ijse.fieldguardianbackend.exception.FileConversionException;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.data.geo.Point;
import org.springframework.web.multipart.MultipartFile;
import java.util.Base64;
/**
 * Utility class for custom ModelMapper converters.
 */
public class ModelMapperConverter {
    /**
     * Converter for converting MultipartFile to Base64 encoded string.
     *
     * @return the converter
     */
    public static Converter<MultipartFile, String> multipartFileToBase64Converter() {
        return new Converter<MultipartFile, String>() {
            @Override
            public String convert(MappingContext<MultipartFile, String> context) {
                MultipartFile source = context.getSource();
                if (source == null) return null;
                try {
                    byte[] bytes = source.getBytes();
                    return Base64.getEncoder().encodeToString(bytes);
                } catch (Exception e) {
                    throw new FileConversionException("Failed to convert MultipartFile to Base64", e);
                }
            }
        };
    }
    /**
     * Converter for converting string to Point object.
     *
     * @return the converter
     */
    public static Converter<String, Point> stringToPointConverter() {
        return new Converter<String, Point>() {
            @Override
            public Point convert(MappingContext<String, Point> context) {
                String source = context.getSource();
                if (source == null || source.isEmpty()) return null;
                return DataConversionUtil.toPoint(source);
            }
        };
    }
}