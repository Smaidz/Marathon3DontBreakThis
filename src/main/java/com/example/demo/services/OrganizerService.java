package com.example.demo.services;


import java.util.ArrayList;

import com.example.demo.model.Marathon;
import com.example.demo.model.Organizer;
import com.example.demo.model.Results;
import com.example.demo.model.User;

public interface OrganizerService{
	
	ArrayList<User> selectAllUsers();
	boolean exportDataExcel();
	boolean insertNewResult(Results results);
	Organizer findByLoginAndPasswordServ(Organizer organizer);
	Organizer selectById_org(long id_org);
	boolean updateOrganizerById_org(Organizer organizer, long id_org);
	boolean deleteOrganizerById_org(long id_org);
	boolean addNewOrganizer(Organizer organizer);
	boolean deleteOrganizerByObject(Organizer organizer);
	boolean changeOrgPassword(Organizer organizer, long id);
	boolean organizerAlreadyExists(Organizer organizer);
	ArrayList<Marathon> selectByOrganizer (long id_org); 

}