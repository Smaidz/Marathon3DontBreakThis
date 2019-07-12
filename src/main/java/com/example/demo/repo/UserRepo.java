package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.User;

public interface UserRepo extends CrudRepository<User, Long>{
	
	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
	//User findByID(long ID);

}
