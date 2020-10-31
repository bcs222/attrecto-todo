package com.attrecto.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attrecto.entities.Task;
import com.attrecto.entities.User;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {
	List<Task> findByUser(User user);
	
	List<Task> findByUserOrderByCompleted(User user);
	
}
