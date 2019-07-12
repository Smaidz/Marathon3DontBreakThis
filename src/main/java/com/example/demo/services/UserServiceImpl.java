package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Marathon;
import com.example.demo.model.User;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	MarathonServiceImpl marathonServiceImpl;  
	@Autowired
	UserRepo userRepo;
	@Autowired
	MarathonRepo marathonRepo;
	
	public boolean addNewUser(User user) {
		if(user != null) {
			User uTemp = userRepo.findByEmail(user.getEmail());
			if(uTemp == null) {
				userRepo.save(user);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public User findByEmailAndPassword(User user) {
		User uTemp = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if (uTemp != null) {
			return uTemp;
		} else {
			return null;
		}
	}
	
	@Override
	public boolean addParticipantToMarathon(long usr_id, long mar_id) {
		User uTemp = userRepo.findById(usr_id).get();
		Marathon mTemp = marathonRepo.findById(mar_id).get();
		if(uTemp != null && mTemp != null) {
			uTemp.addMarathonToCollection(mTemp);
			userRepo.save(uTemp);
			mTemp.addMarathonParticipant(uTemp);
			marathonRepo.save(mTemp);
			return true;
		} else
			return false;
	}
	
	@Override
	public ArrayList<Marathon> findMyMarathons(long usr_id) {
		ArrayList<Marathon> myMarathonsList = marathonServiceImpl.findAllMarathons();
		User uTemp = userRepo.findById(usr_id).get(); 
		ArrayList<Marathon> myMarathonsReturnList = new ArrayList<Marathon>();
		if (uTemp != null && myMarathonsList != null) {
			for (Marathon marathon : myMarathonsList) {
				if(marathon.getMarathonParticipants().contains(uTemp)) {
					myMarathonsReturnList.add(marathon);
				}
			}	
		}
		return myMarathonsReturnList;
}
}
