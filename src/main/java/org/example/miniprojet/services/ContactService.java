package org.example.miniprojet.services;

import org.example.miniprojet.DTO.AdminDTO;
import org.example.miniprojet.DTO.ContactDTO;
import org.example.miniprojet.entities.Admin;
import org.example.miniprojet.entities.Contact;
import org.example.miniprojet.enums.JobTitle;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ContactService {

    List<ContactDTO> getAllContacts();
    Contact getContactById(Long id);
    Contact createContact(ContactDTO contact, MultipartFile photo) throws IOException;
    Contact updateContact(Long id, ContactDTO contact, MultipartFile photo) throws IOException;
    void deleteContact(Long id);
    byte[] getContactPhoto(Long id) throws IOException;
    void sendMailToContact(Long id);
    Contact updateJobTitle(Long id, JobTitle jobTitle);

}
