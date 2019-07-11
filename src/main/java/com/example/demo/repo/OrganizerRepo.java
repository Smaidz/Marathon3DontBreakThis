package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import com.example.demo.model.Organizer;


public interface OrganizerRepo extends CrudRepository<Organizer, Long>{
	ArrayList<Organizer> findByName(String name);
	ArrayList<Organizer> findByLogin(String login);
	ArrayList<Organizer> findByPassword(String password);
	Organizer findByLoginAndPassword(String login, String password);
	Organizer findByLoginAndPasswordAndOrgemail(String login, String password, String orgemail);
	Organizer findByOrgemail(String orgemail);
}
