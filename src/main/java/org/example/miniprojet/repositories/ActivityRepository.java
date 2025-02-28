package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Activity;
import org.example.miniprojet.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    boolean existsByIdAndContactsContains(Long id, Contact contact);
}
