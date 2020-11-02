package com.attrecto.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;
	
	@NotBlank(message = "{error.user.name.blank}")
    @Size(min = 3, max = 20, message = "{error.user.name.size}")
	@Column(unique = true)
	private String name;
	
	@Email(message = "{error.user.name.email.invalid}")
	private String email;
	
	@JsonIgnore
	private String password;
	
	private boolean active;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"), 
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@Transient
	private boolean adminRoleGranted;
	
	@PostLoad
	private void init() {
		Role testAdminRole = new Role();
		testAdminRole.setId(1);;
		if(roles.contains(testAdminRole)) {
			adminRoleGranted = true;
		} else {
			adminRoleGranted = false;
		}
	}
	
	public boolean isAdminRoleGranted() {
		return adminRoleGranted;
	}

	public User() {
		super();
		this.active = true;
	}
	
	public User(String name) {
		this();
		this.name = name;
	}
	
	public User(
			@NotBlank(message = "{error.user.name.blank}") 
			@Size(min = 3, max = 20, message = "{error.user.name.size}") 
			String name,
			
			@Email(message = "Invalid e-mail address...") 
			String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", active=" + active + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public void setAdminRoleGranted(boolean adminRoleGranted) {
		this.adminRoleGranted = adminRoleGranted;
	}
	
}
