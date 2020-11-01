package com.attrecto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.attrecto.controllers.exceptions.UserNotFoundException;
import com.attrecto.entities.User;
import com.attrecto.services.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		try {
			user = userService.getUserByName(username);
			return UserDetailsImpl.build(user);
		} catch (Exception e) {
			throw new UserNotFoundException("User Not Found with username: " + username);
		}
	}

}