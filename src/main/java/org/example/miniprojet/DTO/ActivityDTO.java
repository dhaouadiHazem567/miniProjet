package org.example.miniprojet.DTO;

import org.example.miniprojet.enums.TypeActivity;

import java.util.Date;
import java.util.Set;

public record ActivityDTO(
        Date date,
        TypeActivity typeActivity,
        String subject,
        String notes,
        Set<String> contacts
) {
}
