package lk.ijse.fieldguardianbackend.customObj.impl;

import lk.ijse.fieldguardianbackend.customObj.MailResponse;
import lombok.Builder;

import java.util.Map;

@Builder
public record MailBody(String to, String subject, String templateName, Map<String, Object> replacements) implements MailResponse {}