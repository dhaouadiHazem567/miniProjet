package org.example.miniprojet.repositories;

import org.example.miniprojet.entities.Role;
import org.example.miniprojet.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByRoleName(RoleName role);
}
