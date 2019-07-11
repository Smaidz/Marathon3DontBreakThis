package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Marathon;
import com.example.demo.model.User;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService{
	
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
	
	public ArrayList<Marathon> findAllMarathons() {
		ArrayList<Marathon> tempList = new ArrayList<Marathon>();
		for(Marathon m:marathonRepo.findAll()) {
			tempList.add(m);
		}
		return tempList;
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
	public boolean addParticipantToMarathon(long id, Marathon marathon) {
		User uTemp = userRepo.findById(id).get();
		Marathon mTemp = marathonRepo.findById(marathon.getId()).get();
		System.out.println(uTemp.toString() +""+mTemp.toString());
		if(uTemp != null && mTemp != null) {
			uTemp.addMarathonToCollection(marathon);
			for (Marathon mTempFor: uTemp.getMarathons()) {
				System.out.println("AAAAAAAAAAAA"+mTempFor);
			}
			
			
			userRepo.save(uTemp);
			mTemp.addMarathonParticipant(uTemp);
			marathonRepo.save(mTemp);
			return true;
		} else
			return false;
		
		
		
		
	}

	

	

}
