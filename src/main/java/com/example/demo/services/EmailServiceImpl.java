package com.example.demo.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.model.Organizer;
import com.example.demo.model.User;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private JavaMailSender sender;
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	
	@Override
	public void sendEmail(Organizer organizer) throws MessagingException, IOException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		Iterable<User> users = organizerServiceImpl.selectAllUsers();
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
	@Override
	public void sendWithAttach(String pathToAttachment, Organizer organizer) throws MessagingException, IOException {
		MimeMessage message = sender.createMimeMessage();

		// Enable the multipart flag!
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		Iterable<User> users = organizerServiceImpl.selectAllUsers();
		ArrayList<InternetAddress> listOfToAddress = new ArrayList<InternetAddress>();
		for (User temp : users) {
			if (temp != null) {
				organizerServiceImpl.exportDataExcel();
				listOfToAddress.add(new InternetAddress(temp.getEmail()));
				helper.setTo(temp.getEmail());
				helper.setText("Here are your test results, have a nice day.");
				helper.setSubject("Marathon results");
				FileSystemResource file 
			      = new FileSystemResource(new File("/home/levsgordejevs/Documents/WORKSPACE/Marathon3DontBreakThis-Eduards/src/main/resources/export/MarathonExcel.xlsx"));
				helper.addAttachment("MarathonExcel.xlsx", file);
				sender.send(message);
			}
		}

	}
	
	public void sendToSubs(Organizer organizer) throws MessagingException, IOException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		Iterable<User> users = organizerServiceImpl.selectAllUsers();
		ArrayList<InternetAddress> listOfToAddress = new ArrayList<InternetAddress>();
		for (User temp : users) {
			if (temp != null) {
				if (temp.isSubscribed()) {
					String name = temp.getName();
					System.out.println("THANKS FOR SMASHING THAT SUBSCRIBE BUTTON");
					listOfToAddress.add(new InternetAddress(temp.getEmail()));
					helper.setTo(temp.getEmail());
					helper.setText(
							"Hello there, "+ name+ ", we have some new marathons in! Go to our webpage's marathon list and check them out, you might find something you like!");
					helper.setSubject("New marathons");
					sender.send(message);
				}
			}
		}

	}


}
