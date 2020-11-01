package com.attrecto.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attrecto.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

	Optional<Role> findByName(String roleName);

}
