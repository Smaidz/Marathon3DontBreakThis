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
	public boolean addParticipantToMarathon(long usr_id, long mar_id) {
		User uTemp = userRepo.findById(usr_id).get();
		Marathon mTemp = marathonRepo.findById(mar_id).get();
		System.out.println("SERV-UUUUUU"+uTemp.toString() +"MMMMMMM"+mTemp.toString());
		if(uTemp != null && mTemp != null) {
			uTemp.addMarathonToCollection(mTemp);
			for (Marathon mTempFor: uTemp.getMarathons()) {
				System.out.println("SERV-AAAAAAAAAAAA"+mTempFor);
			}
			
			
			userRepo.save(uTemp);
			mTemp.addMarathonParticipant(uTemp);
			marathonRepo.save(mTemp);
			return true;
		} else
			return false;
		
		
		
		
	}

	

	

}
