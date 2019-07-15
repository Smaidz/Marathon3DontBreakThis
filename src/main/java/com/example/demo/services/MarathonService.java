package com.example.demo.services;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.demo.model.Marathon;

public interface MarathonService {
	
	public ArrayList<Marathon> findAllMarathons();
	boolean insertNewMarathon(Marathon marathon);
	boolean insertNewMarathon(long id, Marathon marathon);
	boolean updateMarathonById(Marathon marathon, long id);
	Marathon selectById(long id);

}
