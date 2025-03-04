package org.example.miniprojet.DTO;

import org.example.miniprojet.entities.Address;
import org.example.miniprojet.enums.JobTitle;

public record ContactDTO(
        Long id,
        String firstname,
        String lastname,
        String email,
        String username,
        String password,
        Long phone,
        JobTitle jobTitle,
        String company,
        Address address,
        String contactOwnerUsername
) {
}
