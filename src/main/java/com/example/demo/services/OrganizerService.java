package com.example.demo.services;


import com.example.demo.model.Marathon;

public interface OrganizerService{
	boolean insertNewMarathon(Marathon marathon);
	boolean updateMarathonById(Marathon marathon, long id);
	Marathon selectById(long id);
	boolean exportDataExcel();
	void sendEmail (String orgemail);
	void sendWithAttach (String orgemail);
}