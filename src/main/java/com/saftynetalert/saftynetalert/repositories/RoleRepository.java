package com.saftynetalert.saftynetalert.repositories;

import com.saftynetalert.saftynetalert.entities.ERole;
import com.saftynetalert.saftynetalert.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ufhopla
 * on 30/05/2022.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
