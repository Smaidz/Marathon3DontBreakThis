package com.example.demo.services;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Marathon;
import com.example.demo.model.Organizer;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.OrganizerRepo;

@Service
public class MarathonServiceImpl implements MarathonService {

	@Autowired
	MarathonRepo marathonRepo;
	@Autowired
	OrganizerRepo organizerRepo;
	
	@Override
	public ArrayList<Marathon> findAllMarathons() {
		ArrayList<Marathon> tempList = new ArrayList<Marathon>();
		for (Marathon m : marathonRepo.findAll()) {
			tempList.add(m);
		}
		return tempList;
	}
	
	@Override
	public boolean insertNewMarathon(Marathon marathon) {
		if(marathon == null) {
			return false;
		}
		Marathon marathonTemp = marathonRepo.findByNameAndDistanceAndPlaceAndDateAndTime(marathon.getName(), marathon.getDistance(), marathon.getPlace(), marathon.getDate(), marathon.getTime());
		if(marathonTemp != null) {
			return false;
		}else {
			marathonRepo.save(marathon);
			return false;
		}
	}
	
	@Override
	public boolean insertNewMarathon(long id, Marathon marathon) {
		if(marathon == null) {
			return false;
		}
		Marathon marathonTemp = marathonRepo.findByNameAndDistanceAndPlaceAndDateAndTime(marathon.getName(), marathon.getDistance(), marathon.getPlace(), marathon.getDate(), marathon.getTime());
		if(marathonTemp != null) {
			return false;
		} else {
			Organizer organizer = organizerRepo.findById(id).get();
			marathon.setOrganizer(organizer);
			marathonRepo.save(marathon);
			return false;
		}
	}
	
	@Override
	public Marathon selectById(long id) {
		
		//FIND ONE
		Marathon carTemp  = marathonRepo.findById(id).get();
		if(carTemp != null) {
			return carTemp;
		}
		
		return null;
	}
	@Override
	public boolean updateMarathonById(Marathon marathon, long id) {
		if(marathonRepo.existsById(id) && marathon != null) {
			Marathon marathonUpdate = marathonRepo.findById(id).get();
			marathonUpdate.setName(marathon.getName());
			marathonUpdate.setDistance(marathon.getDistance());
			marathonUpdate.setPlace(marathon.getPlace());
			marathonUpdate.setDate(marathon.getDate());
			marathonUpdate.setTime(marathon.getTime());
			marathonRepo.save(marathonUpdate);
			return true;
		}
		return false;
	}
	
	
}
