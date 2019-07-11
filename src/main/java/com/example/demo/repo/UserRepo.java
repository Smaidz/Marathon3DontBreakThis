package com.example.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;
@Repository
public interface UserRepo extends CrudRepository<User, Long>{
	
	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
	//User findByID(long ID);

}
