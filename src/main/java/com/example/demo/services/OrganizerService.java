package com.example.demo.services;


import java.util.ArrayList;

import com.example.demo.model.Marathon;
import com.example.demo.model.Organizer;
import com.example.demo.model.Results;
import com.example.demo.model.User;

public interface OrganizerService{
	ArrayList<User> selectAllUsers();
	boolean insertNewMarathon(Marathon marathon);
	boolean updateMarathonById(Marathon marathon, long id);
	Marathon selectById(long id);
	boolean exportAllMarathonsExcel();
	boolean exportOneMarathonExcel(long id);
	boolean insertNewResult(Results results);
	void sendEmail (String orgemail);
	void sendWithAttach (String orgemail);
	ArrayList<Marathon> findMarathonByOrganizerById(long id);
	//ArrayList<Results> findResultsByMarathonId(long id);
}