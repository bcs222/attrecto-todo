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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.attrecto.controllers.exceptions.UserNotFoundException;
import com.attrecto.entities.Task;
import com.attrecto.entities.User;
import com.attrecto.services.TaskService;
import com.attrecto.services.UserService;

@RestController
public class TaskRestController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	private EntityModel<Task> createTaskEntityModel(Task task){
		int userId = task.getUser().getId();
		int taskId = task.getId();
		
		Link taskLink = linkTo(methodOn(TaskRestController.class).getTask(userId, taskId)).withSelfRel();
		Link addTaskLink = linkTo(methodOn(TaskRestController.class).addTask(task)).withRel("add");
		Link updateTaskLink = linkTo(methodOn(TaskRestController.class).updateTask(task)).withRel("update");
		Link deleteTaskLink = linkTo(methodOn(TaskRestController.class).deleteTask(userId, taskId)).withRel("delete");
		Link taskListLink = linkTo(methodOn(TaskRestController.class).getTaskList(userId)).withRel("taskList");
		Link userLink = linkTo(methodOn(UserRestController.class).getUser(userId)).withRel("user");
		Link userListLink = linkTo(methodOn(UserRestController.class).getUserList()).withRel("userList");
		
		return EntityModel.of(
				task, taskLink, addTaskLink, updateTaskLink,  deleteTaskLink, userLink, taskListLink, userListLink);
	}

	@GetMapping("/users/{userId}/tasks/{taskId}")
	public EntityModel<Task> getTask(@PathVariable int userId, @PathVariable int taskId) {
		Task task = taskService.findTaskById(taskId);
		
		return createTaskEntityModel(task);
	}

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
	
	@PostMapping("/users/{userId}/tasks")
	public EntityModel<Task> addTask(@RequestBody @Valid Task task){
		return createTaskEntityModel(taskService.saveTask(task));
	}
	
	@PutMapping("/users/{userId}/tasks")
	public EntityModel<Task> updateTask(@RequestBody @Valid Task task){
		return createTaskEntityModel(taskService.saveTask(task));
	}
	
	@DeleteMapping("/users/{userId}/tasks/{taskId}")
	public EntityModel<Task> deleteTask(@PathVariable int userId, @PathVariable int taskId){
		return createTaskEntityModel(taskService.deleteTask(taskId));
	}
}