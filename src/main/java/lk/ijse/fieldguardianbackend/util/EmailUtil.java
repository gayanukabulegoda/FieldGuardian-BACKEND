package lk.ijse.fieldguardianbackend.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lk.ijse.fieldguardianbackend.customObj.impl.MailBody;
import lk.ijse.fieldguardianbackend.exception.EmailVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Map;
import java.util.Random;
/**
 * This class is responsible for sending HTML emails with dynamic values from a template
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailUtil {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String fromEmail;
    /**
     * Method to load the HTML template and replace multiple placeholders
     * @param templateName The name of the template file
     * @param replacements The key-value pairs to replace in the template
     */
    private String loadHtmlTemplate(String templateName, Map<String, Object> replacements) {
        Context context = new Context();
        context.setVariables(replacements);
        return templateEngine.process(templateName, context);
    }
    /**
     * Send an HTML email with dynamic values from a template
     * @param mailBody The mail body object containing the email details
     */
    public void sendHtmlMessage(MailBody mailBody) {
        new Thread(() -> {
            try {
                sendHtmlMail(mailBody);
            } catch (MessagingException e) {
                throw new EmailVerificationException("Error sending email", e);
            }
        }).start();
    }
    private void sendHtmlMail(MailBody mailBody) throws MessagingException {
        String htmlContent = loadHtmlTemplate(mailBody.templateName(), mailBody.replacements());
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multipart
        helper.setTo(mailBody.to());
        helper.setFrom(fromEmail);
        helper.setSubject(mailBody.subject());
        helper.setText(htmlContent, true); // true indicates HTML
        javaMailSender.send(message);
        log.info("HTML Mail Sent");
    }
    /**
     * Generate a random 6-digit OTP
     * @return The generated OTP
     */
    public Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}