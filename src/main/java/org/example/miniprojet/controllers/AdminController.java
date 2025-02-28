package org.example.miniprojet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.miniprojet.DTO.AdminDTO;
import org.example.miniprojet.entities.Admin;
import org.example.miniprojet.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/getAllAdmins")
    public ResponseEntity<List<Admin>> getAllAdmins(){
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/getAdmin/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable("id") Long id){
        try {
            return ResponseEntity.ok(adminService.getAdminById(id));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/addAdmin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Admin> addAdmin(@RequestParam String adminJson, @RequestParam(value = "photo", required = false) MultipartFile photo){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AdminDTO admin = objectMapper.readValue(adminJson, AdminDTO.class);
            return ResponseEntity.ok(adminService.createAdmin(admin, photo));
        }
        catch (IllegalArgumentException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(value = "/modifyAdmin/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Admin> modifyAdmin(@PathVariable Long id, @RequestParam String adminJson,
                                              @RequestParam(value = "photo", required = false) MultipartFile photo){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            AdminDTO admin = objectMapper.readValue(adminJson, AdminDTO.class);
            return ResponseEntity.ok(adminService.updateAdmin(id, admin, photo));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/deleteAdmin/{id}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long id){
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.ok("Admin deleted sucessfully");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/getAdminPhoto/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getAdminPhoto(@PathVariable Long id){
        try {
            return ResponseEntity.ok(adminService.getAdminPhoto(id));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
