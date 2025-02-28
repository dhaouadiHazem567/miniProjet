package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Admin> findAdminByUsernameOrEmail(String username, String email);
}
