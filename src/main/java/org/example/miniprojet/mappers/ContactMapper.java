package org.example.miniprojet.mappers;

import org.example.miniprojet.DTO.ContactDTO;
import org.example.miniprojet.entities.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactMapper {

    public ContactDTO toDTO(Contact contact) {
        if (contact == null) {
            return null;
        }

        String contactOwnerUsername = null;
        if(contact.getContactOwner() != null)
            contactOwnerUsername = contact.getContactOwner().getUsername();

        return new ContactDTO(
                contact.getId(),
                contact.getFirstname(),
                contact.getLastname(),
                contact.getEmail(),
                contact.getUsername(),
                contact.getPassword(),
                contact.getPhone(),
                contact.getJobTitle(),
                contact.getCompany(),
                contact.getAddress(),
                contactOwnerUsername
        );
    }
}
