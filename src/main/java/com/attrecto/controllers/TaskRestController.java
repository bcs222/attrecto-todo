package com.attrecto.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.attrecto.controllers.exceptions.TaskNotFoundException;
import com.attrecto.controllers.exceptions.UserNotFoundException;
import com.attrecto.entities.Task;
import com.attrecto.entities.User;
import com.attrecto.services.TaskService;
import com.attrecto.services.UserService;

@RestController
public class TaskRestController {
	@Autowired
	private UserService userService;
	
	private EntityModel<Task> createTaskEntityModel(Task task){
		int userId = task.getUser().getId();
		
		EntityModel<Task> taskEntity = methodOn(TaskRestController.class).getTask(task.getId());
		EntityModel<User> userEntity = methodOn(UserRestController.class).getUser(userId);
		CollectionModel<EntityModel<Task>> taskList = methodOn(TaskRestController.class).getTaskList(userId);
		CollectionModel<EntityModel<User>> userList = methodOn(UserRestController.class).getUserList();
		
		Link taskLink = linkTo(taskEntity).withSelfRel();
		Link userLink = linkTo(userEntity).withRel("user");
		Link taskListLink = linkTo(taskList).withRel("taskList");
		Link userListLink = linkTo(userList).withRel("userList");
		
		return EntityModel.of(task, taskLink, userLink, taskListLink, userListLink);
	}
	
	@Autowired
	private TaskService taskService;

	@GetMapping("/users/{userId}/tasks")
	public CollectionModel<EntityModel<Task>> getTaskList(@PathVariable int userId){
		User user = userService.getUserById(userId);
		if(user == null) {
			throw new UserNotFoundException();
		}
		
		List<Task> taskList = taskService.findTaskByUser(user);
		List<EntityModel<Task>> listResources = taskList
				.stream()
				.map(this::createTaskEntityModel)
				.collect(Collectors.toList());
		
		Link selfRel = linkTo(methodOn(TaskRestController.class).getTaskList(userId)).withSelfRel();
		return CollectionModel.of(listResources, selfRel);
		
	}
	
	@GetMapping("/tasks/{taskId}")
	public EntityModel<Task> getTask(@PathVariable int taskId){
		Task task = taskService.findTaskById(taskId);
		
		if(task == null) {
			throw new TaskNotFoundException();
		}
		
		return createTaskEntityModel(task);
	}
}