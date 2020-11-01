package com.attrecto.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.attrecto.entities.User;
import com.attrecto.security.UserDetailsImpl;
import com.attrecto.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	private EntityModel<User> createUserEntityModel(User user) {
		Link selfLink = linkTo(methodOn(UserRestController.class).getUser(user.getId())).withSelfRel();
		Link userListLink = linkTo(methodOn(UserRestController.class).getUserList()).withRel("allUsers");
		Link userTaskListLink = linkTo(methodOn(TaskRestController.class).getTaskList(user.getId())).withRel("taskList");
		
		return EntityModel.of(user, selfLink, userTaskListLink, userListLink);
	}
	
	private boolean accessAllowed(Authentication authentication, HttpRequest request) {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Integer id = userDetails.getId(); 
		boolean isAdmin = userDetails.getAuthorities().stream()
		.map(a -> a.getAuthority())
		.collect(Collectors.toSet()).contains("ROLE_ADMIN");
		
		if(isAdmin) {
			return true;
		}
		
		if(request.getURI().toString().contains("/users/" + id + "/")) {
			return true;
		}
		
		if(request.getURI().toString().endsWith("/users/" + id)) {
			return true;
		}
		
		return false;
	}

	@GetMapping
	public CollectionModel<EntityModel<User>> getUserList() {
		List<EntityModel<User>> userEntityModelList = 
				userService.getAllUsers().stream()
				.map(this::createUserEntityModel)
				.collect(Collectors.toList());
		
		Link selfRel = linkTo(methodOn(this.getClass()).getUserList()).withSelfRel();
		
		return CollectionModel.of(userEntityModelList, selfRel);
	}

	@GetMapping("/{userId}")
	public EntityModel<User> getUser(@PathVariable int userId) {
		return createUserEntityModel(userService.getUserById(userId));
	}
	
	@PostMapping
	public EntityModel<User> addUser(@RequestBody @Valid User user){
		return createUserEntityModel(userService.save(user));
	}
	
	@PutMapping
	public EntityModel<User> updateUser(@RequestBody @Valid User user){
		return createUserEntityModel(userService.save(user));
	}
	
	@DeleteMapping("/{userId}")
	public EntityModel<User> deleteUser(@PathVariable int userId) {
		return createUserEntityModel(userService.delete(userId));
	}
}
