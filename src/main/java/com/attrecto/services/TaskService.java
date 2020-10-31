package com.attrecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attrecto.controllers.exceptions.TaskNotFoundException;
import com.attrecto.entities.Task;
import com.attrecto.entities.User;
import com.attrecto.repos.TaskRepo;

@Service
public class TaskService {
	@Autowired
	private TaskRepo taskRepo;
	
	public List<Task> findTaskByUser(User user) {
		List<Task> tasks = taskRepo.findByUserOrderByCompleted(user);
		
		return tasks;
	}
	
	public Task findTaskById(int taskId) {		
		return taskRepo.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
	}
	
	public Task saveTask(Task task) {
		return taskRepo.save(task);
	}
	
	public void deleteTask(Task task) {
		taskRepo.delete(task);
	}
	
	public void deleteTask(int id) {
		taskRepo.deleteById(id);
	}
}
