package com.example.demo.services;

import java.util.ArrayList;

import com.example.demo.model.Organizer;

public interface AdminService {
	ArrayList<Organizer> selectAll();
	Organizer selectById_org(long id_org);
	boolean addNewOrganizer(Organizer organizer);
	boolean updateOrganizerById_org(Organizer organizer, long id_org);
	boolean deleteOrganizerById_org(long id_org);
	boolean deleteOrganizerByObject(Organizer organizer);
	
}
