package lk.ijse.fieldguardianbackend.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import lk.ijse.fieldguardianbackend.entity.impl.IdCounter;
import lk.ijse.fieldguardianbackend.repository.IdCounterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Utility class for generating custom IDs with a specific prefix and date format.
 */
@Component
@RequiredArgsConstructor
public class CustomIdGenerator {
    private static final String DATE_FORMAT = "yyyyMMdd";
    private final IdCounterRepository idCounterRepository;
    /**
     * Generates a custom ID with the given prefix.
     *
     * @param prefix the prefix for the ID
     * @return the generated ID
     */
    @Transactional
    public String generateId(String prefix) {
        String date = new SimpleDateFormat(DATE_FORMAT).format(new Date());
        // Retrieve the current counter value for the specified prefix, or initialize it if it does not exist
        IdCounter idCounter = idCounterRepository.findByPrefix(prefix)
                .orElseGet(() -> {
                    IdCounter newCounter = new IdCounter();
                    newCounter.setPrefix(prefix);
                    newCounter.setLastCount(0);
                    return newCounter;
                });
        // Increment the counter and update the database
        int count = idCounter.getLastCount() + 1;
        idCounter.setLastCount(count);
        idCounterRepository.save(idCounter);
        // Return the generated ID
        return prefix + "-" + date + "-" + String.format("%03d", count);
    }
}