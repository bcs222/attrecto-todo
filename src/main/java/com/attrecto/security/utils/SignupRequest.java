package com.attrecto.security.utils;

import java.io.Serializable;
import java.util.Set;

public class SignupRequest implements Serializable{
	private static final long serialVersionUID = 1L;

	private String username;
	private String email;
	private String password;
	private Set<String> roles;
	
	public SignupRequest() {}
	
	public SignupRequest(String username, String email, Set<String> roles) {
		super();
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}
