package com.example.demo.services;

import java.util.ArrayList;

import com.example.demo.model.Organizer;
import com.example.demo.model.Results;
import com.example.demo.model.User;

public interface AdminService {
	
	ArrayList<Organizer> selectAll();
	void uploadTestData();
	void adminUpdateUserById(User user, long id);
	ArrayList<Results> getAllResults();
	
	
}
