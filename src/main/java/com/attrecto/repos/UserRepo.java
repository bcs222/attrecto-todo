package com.attrecto.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.attrecto.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

}
