package com.attrecto.security.utils;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String jwt;
	private int userId;
	private String username;
	private String email;
	private List<String> roles;
	
	public JwtResponse() {}
	
	public JwtResponse(String jwt, int userId, String username, String email, List<String> roles) {
		super();
		this.jwt = jwt;
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
}
