package org.example.miniprojet.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.miniprojet.DTO.ContactDTO;
import org.example.miniprojet.entities.Contact;
import org.example.miniprojet.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/getAllContacts")
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.ok(contactService.getAllContacts());
    }

    @GetMapping("/getContact/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(contactService.getContactById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "/addContact", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Contact> addContact(@RequestParam String contactJson, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ContactDTO contact = objectMapper.readValue(contactJson, ContactDTO.class);
            return ResponseEntity.ok(contactService.createContact(contact, photo));
        } catch (IllegalArgumentException | JsonProcessingException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping(path = "/modifyContact/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Contact> modifyContact(@PathVariable Long id, @RequestParam String contactJson,
                                                 @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ContactDTO contact = objectMapper.readValue(contactJson, ContactDTO.class);
            return ResponseEntity.ok(contactService.updateContact(id, contact, photo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/deleteContact/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {
        try {
            contactService.deleteContact(id);
            return ResponseEntity.ok("Contact deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/getContactPhoto/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<byte[]> getContactPhoto(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(contactService.getContactPhoto(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
