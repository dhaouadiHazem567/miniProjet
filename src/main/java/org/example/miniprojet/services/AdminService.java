package org.example.miniprojet.services;

import org.example.miniprojet.DTO.AdminDTO;
import org.example.miniprojet.entities.Admin;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AdminService {

    List<Admin> getAllAdmins();
    Admin getAdminById(Long id);
    Admin createAdmin(AdminDTO admin, MultipartFile photo) throws IOException;
    Admin updateAdmin(Long id, AdminDTO admin, MultipartFile photo) throws IOException;
    void deleteAdmin(Long id);
    byte[] getAdminPhoto(Long id) throws IOException;

}
