package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Organizer;
import com.example.demo.services.OrganizerServiceImpl;
import com.example.demo.services.EmailServiceImpl;
@Controller
public class EmailController {

	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	@Autowired 
	EmailServiceImpl emailServiceImpl;

	@RequestMapping("/sendemail")
	@ResponseBody
	String home(Organizer organizer) {
		try {
			emailServiceImpl.sendEmail(organizer);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}


	@RequestMapping("/sendwithattach")
	@ResponseBody
	String home2(String pathtoAttach, Organizer organizer) {
		try {
			emailServiceImpl.sendWithAttach(pathtoAttach, organizer);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}


	@RequestMapping("/sendtosubs")
	@ResponseBody
	String home3(Organizer organizer) {
		try {
			emailServiceImpl.sendToSubs(organizer);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}


}