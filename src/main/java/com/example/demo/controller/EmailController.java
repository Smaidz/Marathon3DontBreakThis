package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Organizer;
import com.example.demo.model.User;
import com.example.demo.repo.OrganizerRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.services.OrganizerServiceImpl;

@Controller
public class EmailController {

	@Autowired
	private JavaMailSender sender;
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	@Autowired
	OrganizerRepo organizerRepo;
	@Autowired
	UserRepo userRepo;

	@RequestMapping("/sendemail")
	@ResponseBody
	String home(Organizer organizer) {
		try {
			sendEmail(organizer);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}

	private void sendEmail(Organizer organizer) throws MessagingException, IOException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		Iterable<User> users = userRepo.findAll();
		ArrayList<InternetAddress> listOfToAddress = new ArrayList<InternetAddress>();
		for (User temp : users) {
			if (temp != null) {
				listOfToAddress.add(new InternetAddress(temp.getEmail()));
				helper.setTo(temp.getEmail());
				helper.setText("How are you?");
				helper.setSubject("Hi");
				sender.send(message);
			}
		}
	}

	@RequestMapping("/sendwithattach")
	@ResponseBody
	String home2(Organizer organizer) {
		try {
			sendWithAttach(organizer);
			return "Email Sent!";
		} catch (Exception ex) {
			return "Error in sending email: " + ex;
		}
	}

	public void sendWithAttach(Organizer organizer) throws MessagingException, IOException {
		MimeMessage message = sender.createMimeMessage();

		// Enable the multipart flag!
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		Iterable<User> users = userRepo.findAll();
		ArrayList<InternetAddress> listOfToAddress = new ArrayList<InternetAddress>();
		for (User temp : users) {
			if (temp != null) {
				organizerServiceImpl.exportAllMarathonsExcel();
				listOfToAddress.add(new InternetAddress(temp.getEmail()));
				helper.setTo(temp.getEmail());
				helper.setText("Here are your test results, have a nice day.");
				helper.setSubject("Marathon results");
				ClassPathResource file = new ClassPathResource("MarathonExcel.xlsx");
				helper.addAttachment("MarathonExcel.xlsx", file);
				sender.send(message);
			}
		}

	}
}