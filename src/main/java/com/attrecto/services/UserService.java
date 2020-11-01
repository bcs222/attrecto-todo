package com.attrecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attrecto.controllers.exceptions.UserNotFoundException;
import com.attrecto.entities.User;
import com.attrecto.repos.TaskRepo;
import com.attrecto.repos.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TaskRepo taskRepo;
	
	public User getUserById(int userId) {
		return userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
	}
	
	public User getUserByName(String userName) {
		try {
			return userRepo.findByName(userName.trim());
		} catch (Exception e) {
			throw new UserNotFoundException(userName);
		}
	}
	
	public boolean existsByUsername(String userName) {
		try {
			return userRepo.findByName(userName.trim()) != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean existsByEmail(String userEmail) {
		try {
			return userRepo.findByEmail(userEmail.trim()) != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	public User save(User user) {
		return userRepo.save(user);
	}
	
	public User delete(int id) {
		User user = getUserById(id);
		taskRepo.deleteAll(taskRepo.findByUser(user));
		user.setActive(false);
		return save(user);
	}
}
