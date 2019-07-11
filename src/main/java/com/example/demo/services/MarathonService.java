package com.example.demo.services;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.demo.model.Marathon;

public interface MarathonService {
	
	public ArrayList<Marathon> findAllMarathons();

}
