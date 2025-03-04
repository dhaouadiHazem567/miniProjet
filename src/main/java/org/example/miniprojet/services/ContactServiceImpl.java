package org.example.miniprojet.services;

import jakarta.transaction.Transactional;
import org.example.miniprojet.DTO.ContactDTO;
import org.example.miniprojet.entities.Contact;
import org.example.miniprojet.entities.Role;
import org.example.miniprojet.entities.User;
import org.example.miniprojet.enums.FileType;
import org.example.miniprojet.enums.JobTitle;
import org.example.miniprojet.enums.RoleName;
import org.example.miniprojet.mappers.ContactMapper;
import org.example.miniprojet.repositories.ContactRepository;
import org.example.miniprojet.repositories.RoleRepository;
import org.example.miniprojet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ContactMapper contactMapper;

    @Override
    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll().stream().map(
                contact -> contactMapper.toDTO(contact)
        ).toList();
    }

    @Override
    public Contact getContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact createContact(ContactDTO contact, MultipartFile photo) throws IOException {
        if(contact == null)
            throw new IllegalArgumentException("Contact cannot be null");

        if(contactRepository.existsByEmail(contact.email()))
            throw new IllegalArgumentException("Contact with this email already exists");

        if(contactRepository.existsByUsername(contact.username()))
            throw new IllegalArgumentException("Contact with this username already exists");

        Role role = roleRepository.findRoleByRoleName(RoleName.ROLE_CONTACT).orElse(null);
        if(role == null) {
            role = new Role();
            role.setRoleName(RoleName.ROLE_CONTACT);
            roleRepository.save(role);
        }

        Contact contactToSave = new Contact();
        contactToSave.setFirstname(contact.firstname());
        contactToSave.setLastname(contact.lastname());
        contactToSave.setEmail(contact.email());
        contactToSave.setUsername(contact.username());
        contactToSave.setPassword(contact.password());
        contactToSave.setPhone(contact.phone());
        contactToSave.setJobTitle(contact.jobTitle());
        contactToSave.setCompany(contact.company());
        contactToSave.setAddress(contact.address());
        contactToSave.getRoles().add(role);

        User user = userRepository.findUserByUsername(contact.contactOwnerUsername()).orElse(null);
        if(user != null)
            contactToSave.setContactOwner(user);

        if(photo != null && !photo.isEmpty())
            contactToSave.setPhotoPath(FileManager.savePhoto(photo, contact.username()));

        Contact contact1 = contactRepository.save(contactToSave);
        if(contact.email() != null)
            sendMailToContact(contact1.getId());
        return contact1;
    }

    @Override
    public Contact updateContact(Long id, ContactDTO contact, MultipartFile photo) throws IOException {
        Optional<Contact> optionalExitstingContact = contactRepository.findById(id);
        if(optionalExitstingContact.isEmpty())
            throw new IllegalArgumentException("Contact with ID "+id+" not found");

        Contact contactToUpdate = contactRepository.findById(id).get();

        Contact contact1 = contactRepository.findContactByEmailOrUsername(contact.username(), contact.email()).orElse(null);

        if(contact1 != null && contact1.getId() != id)
            throw new IllegalArgumentException("Contact with this username or email already exists");

        contactToUpdate.setFirstname(contact.firstname());
        contactToUpdate.setLastname(contact.lastname());
        contactToUpdate.setEmail(contact.email());
        contactToUpdate.setPassword(contact.password());
        contactToUpdate.setPhone(contact.phone());
        contactToUpdate.setJobTitle(contact.jobTitle());
        contactToUpdate.setCompany(contact.company());
        contactToUpdate.setAddress(contact.address());

        User user = userRepository.findUserByUsername(contact.contactOwnerUsername()).orElse(null);
        if(user != null && user.getId() != id)
            contactToUpdate.setContactOwner(user);

        if(photo != null && !photo.isEmpty())
            contactToUpdate.setPhotoPath(FileManager.savePhoto(photo, contact.username()));

        return contactRepository.save(contactToUpdate);
    }

    @Override
    public void deleteContact(Long id) {
        Optional<Contact> optional = contactRepository.findById(id);

        if(optional.isEmpty())
            throw new IllegalArgumentException("Contact with ID "+id+" not found");

        contactRepository.deleteById(id);
    }

    @Override
    public byte[] getContactPhoto(Long id) throws IOException {
        Contact contact = contactRepository.findById(id).orElse(null);
        if(contact == null && contact.getPhotoPath() == null)
            throw new RuntimeException("Photo not found");

        return FileManager.getFile(contact.getPhotoPath());
    }

    @Override
    public void sendMailToContact(Long id) {
        Optional<Contact> optional = contactRepository.findById(id);
        if(optional.isPresent() && !optional.get().getEmail().isEmpty()){
            Contact contact = optional.get();
            emailService.sendMailToContact(contact.getEmail(), contact.getUsername(), contact.getPassword());
        }
    }

    @Override
    public Contact updateJobTitle(Long id, JobTitle jobTitle) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if(contact == null)
            return null;

        contact.setJobTitle(jobTitle);
        return contactRepository.save(contact);
    }
}
