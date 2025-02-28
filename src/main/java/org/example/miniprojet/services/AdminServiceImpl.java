package org.example.miniprojet.services;

import jakarta.transaction.Transactional;
import org.example.miniprojet.DTO.AdminDTO;
import org.example.miniprojet.entities.Admin;
import org.example.miniprojet.entities.Role;
import org.example.miniprojet.enums.FileType;
import org.example.miniprojet.enums.RoleName;
import org.example.miniprojet.repositories.AdminRepository;
import org.example.miniprojet.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @Override
    public Admin getAdminById(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin createAdmin(AdminDTO admin, MultipartFile photo) throws IOException {
        if(admin == null)
            throw new IllegalArgumentException("Admin cannot be null");

        if(adminRepository.existsByEmail(admin.email()))
            throw new IllegalArgumentException("Admin with this email already exists");

        if(adminRepository.existsByUsername(admin.username()))
            throw new IllegalArgumentException("Admin with this username already exists");

        Role role = roleRepository.findRoleByRoleName(RoleName.ROLE_ADMIN).orElse(null);
        if(role == null) {
            role = new Role();
            role.setRoleName(RoleName.ROLE_ADMIN);
            roleRepository.save(role);
        }

        Admin adminToSave = new Admin();
        adminToSave.setFirstname(admin.firstname());
        adminToSave.setLastname(admin.lastname());
        adminToSave.setEmail(admin.email());
        adminToSave.setUsername(admin.username());
        adminToSave.setPassword(bCryptPasswordEncoder.encode(admin.password()));
        adminToSave.getRoles().add(role);

        if(photo != null && !photo.isEmpty())
            adminToSave.setPhotoPath(FileManager.savePhoto(photo, admin.username()));

        return adminRepository.save(adminToSave);
    }

    @Override
    public Admin updateAdmin(Long id, AdminDTO admin, MultipartFile photo) throws IOException {
        Optional<Admin> optionalExitstingAdmin = adminRepository.findById(id);
        if(optionalExitstingAdmin.isEmpty())
            throw new IllegalArgumentException("Admin with ID "+id+" not found");

        Admin adminToUpdate = adminRepository.findById(id).get();

        Admin admin1 = adminRepository.findAdminByUsernameOrEmail(admin.username(), admin.email()).orElse(null);

        if(admin1 != null && admin1.getId() != id)
            throw new IllegalArgumentException("Admin with this username or email already exists");

        adminToUpdate.setFirstname(admin.firstname());
        adminToUpdate.setLastname(admin.lastname());
        adminToUpdate.setEmail(admin.email());
        adminToUpdate.setPassword(admin.password());

        if(!photo.isEmpty() && photo != null)
            adminToUpdate.setPhotoPath(FileManager.savePhoto(photo, adminToUpdate.getUsername()));

        return adminRepository.save(adminToUpdate);
    }

    @Override
    public void deleteAdmin(Long id) {
        Optional<Admin> optional = adminRepository.findById(id);

        if(optional.isEmpty())
            throw new IllegalArgumentException("Admin with ID "+id+" not found");

        adminRepository.deleteById(id);
    }

    @Override
    public byte[] getAdminPhoto(Long id) throws IOException {
        Admin admin = adminRepository.findById(id).orElse(null);
        if(admin == null && admin.getPhotoPath() == null)
            throw new RuntimeException("Photo not found");

        return FileManager.getFile(admin.getPhotoPath());
    }
}
