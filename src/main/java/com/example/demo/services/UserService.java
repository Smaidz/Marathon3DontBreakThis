package com.example.demo.services;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.demo.model.Marathon;
import com.example.demo.model.Results;
import com.example.demo.model.User;

public interface UserService {
	
	boolean addNewUser(User user);
	User findByEmailAndPassword(User user);
	boolean addParticipantToMarathon(long usr_id, long mar_id);
	ArrayList<Marathon> findMyMarathons(long usr_id);
	User findByID(long usr_id);
	void updateUserById(User user, long id);
	ArrayList<Results>  findResByUserId(long id);
}
