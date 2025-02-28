package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Contact> findContactByEmailOrUsername(String email, String username);
    Optional<Contact> findContactByUsername(String username);

}
