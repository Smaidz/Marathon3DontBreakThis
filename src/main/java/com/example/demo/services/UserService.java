package com.example.demo.services;

import java.util.ArrayList;

import com.example.demo.model.Marathon;
import com.example.demo.model.User;

public interface UserService {
	
	boolean addNewUser(User user);
	ArrayList<Marathon> findAllMarathons();
	User findByEmailAndPassword(User user);

}
