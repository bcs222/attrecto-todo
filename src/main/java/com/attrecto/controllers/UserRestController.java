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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.attrecto.entities.User;
import com.attrecto.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserRestController {

	@Autowired
	private UserService userService;

	private EntityModel<User> createUserEntityModel(User user) {
		int userId = user.getId();
		Link selfLink = linkTo(methodOn(UserRestController.class).getUser(userId)).withSelfRel();
		Link addUserLink = linkTo(methodOn(UserRestController.class).addUser(user)).withRel("add");
		Link updateUserLink = linkTo(methodOn(UserRestController.class).updateUser(user)).withRel("update");
		Link deleteUserLink = linkTo(methodOn(UserRestController.class).deleteUser(userId)).withRel("delete");
		Link userListLink = linkTo(methodOn(UserRestController.class).getUserList()).withRel("allUsers");
		Link userTaskListLink = linkTo(methodOn(TaskRestController.class).getTaskList(userId)).withRel("taskList");
		
		return EntityModel.of(user, selfLink, addUserLink, updateUserLink, deleteUserLink, userTaskListLink, userListLink);
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public CollectionModel<EntityModel<User>> getUserList() {
		List<EntityModel<User>> userEntityModelList = 
				userService.getAllUsers().stream()
				.map(this::createUserEntityModel)
				.collect(Collectors.toList());
		
		Link selfRel = linkTo(methodOn(this.getClass()).getUserList()).withSelfRel();
		
		return CollectionModel.of(userEntityModelList, selfRel);
	}

	@GetMapping("/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public EntityModel<User> getUser(@PathVariable int userId) {
		User userById = userService.getUserById(userId);
		return createUserEntityModel(userById);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public EntityModel<User> addUser(@RequestBody @Valid User user){
		return createUserEntityModel(userService.save(user));
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ADMIN')")
	public EntityModel<User> updateUser(@RequestBody @Valid User user){
		return createUserEntityModel(userService.save(user));
	}
	
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public EntityModel<User> deleteUser(@PathVariable int userId) {
		return createUserEntityModel(userService.delete(userId));
	}
}
