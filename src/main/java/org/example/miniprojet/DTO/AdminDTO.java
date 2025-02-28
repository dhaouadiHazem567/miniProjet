package org.example.miniprojet.DTO;


import org.springframework.web.multipart.MultipartFile;

public record AdminDTO(
        String firstname,
        String lastname,
        String email,
        String username,
        String password
) {
}
