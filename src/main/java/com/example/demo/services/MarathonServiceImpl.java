package com.example.demo.services;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Marathon;
import com.example.demo.repo.MarathonRepo;

@Service
public class MarathonServiceImpl {

	@Autowired
	MarathonRepo marathonRepo;

	public ArrayList<Marathon> findAllMarathons() {
		ArrayList<Marathon> tempList = new ArrayList<Marathon>();
		for (Marathon m : marathonRepo.findAll()) {
			tempList.add(m);
		}
		return tempList;
	}
	
	
}
