package com.example.demo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Marathon;
import com.example.demo.model.Results;
import com.example.demo.model.User;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.ResultsRepo;
import com.example.demo.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	MarathonServiceImpl marathonServiceImpl;
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	@Autowired
	UserRepo userRepo;
	@Autowired
	MarathonRepo marathonRepo;
	@Autowired
	ResultsRepo resultsRepo;
	
	@Override
	public ArrayList<Results> findResByUserId(long id) {

		User user = userRepo.findById(id).get();
		System.out.println(id);
		System.out.println(user);


		if (user != null) {
			ArrayList<Results> myResultsList = resultsRepo.findByUser(user);
			
			
			System.out.println(myResultsList.size());
		
			if (myResultsList != null) {
					return myResultsList;
				}
			else
				return new ArrayList<Results>();
			} else {
				
				return new ArrayList<Results>();
			}
		
		
	}

	public boolean addNewUser(User user) {
		if (user != null) {
			User uTemp = userRepo.findByEmail(user.getEmail());
			if (uTemp == null) {
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
		Collection<Marathon> mcTemp = uTemp.getMarathons();
		if (uTemp != null && mTemp != null && mcTemp != null) {
			if(mcTemp.contains(mTemp)) 
				return false;
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
				if (marathon.getMarathonParticipants().contains(uTemp)) {
					myMarathonsReturnList.add(marathon);
				}
			}
		}
		return myMarathonsReturnList;
	}

	@Override
	public User findByID(long usr_id) {
		User uTemp = userRepo.findById(usr_id).get();
		return uTemp;
	}
	@Override
	public void updateUserById(User user, long id) {
		if(userRepo.existsById(id) && user != null) {
			User userUpdate = userRepo.findById(id).get();
			userUpdate.setName(user.getName());
			userUpdate.setSurname(user.getSurname());
			userUpdate.setEmail(user.getEmail());
			userUpdate.setPassword(user.getPassword());
			userUpdate.setBirthDate(userUpdate.getBirthDate());
			userUpdate.setIsSubscribed(user.getIsSubscribed());
			userRepo.save(userUpdate);
		}
	}
	
	@Override
	public ArrayList<User> selectAllUsers() {
		ArrayList<User> tempList = new ArrayList<User>();
		for (User o:userRepo.findAll())
		{
			tempList.add(o);
		}
		return tempList;
	}
	
	
}
