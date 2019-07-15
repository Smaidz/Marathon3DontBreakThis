package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Organizer;
import com.example.demo.repo.OrganizerRepo;

@Service
public class AdminServiceIMPL implements AdminService{

	@Autowired
	OrganizerRepo organizerRepo;
	
	@Override
	public ArrayList<Organizer> selectAll() {
		ArrayList<Organizer> tempList = new ArrayList<Organizer>();
		for (Organizer o:organizerRepo.findAll())
		{
			tempList.add(o);
		}
		return tempList;
	}
}
