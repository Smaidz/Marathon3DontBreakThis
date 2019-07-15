package com.example.demo.services;

import java.io.IOException;

import javax.mail.MessagingException;

import com.example.demo.model.Organizer;

public interface EmailService {

	void sendEmail (Organizer organizer) throws MessagingException, IOException;
	void sendToSubs (Organizer organizer) throws MessagingException, IOException;
	void sendWithAttach(String pathToAttachment, Organizer organizer) throws MessagingException, IOException;
}