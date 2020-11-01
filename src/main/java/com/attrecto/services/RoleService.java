package com.attrecto.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attrecto.entities.Role;
import com.attrecto.repos.RoleRepo;

@Service
public class RoleService {
	@Autowired
	private RoleRepo roleRepo;
	
	public Optional<Role> findByName(String roleName) {
		return roleRepo.findByName(roleName);
	}
}
