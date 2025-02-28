package org.example.miniprojet.DTO;

import org.example.miniprojet.enums.TypeActivity;

import java.util.Date;

public record ActivityDTO(
        Date date,
        TypeActivity typeActivity,
        String subject,
        String notes
) {
}
