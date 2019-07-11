package com.example.demo.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Organizer;
@Repository
public interface OrganizerRepo extends CrudRepository<Organizer, Long>{
	ArrayList<Organizer> findByName(String name);
	ArrayList<Organizer> findByLogin(String login);
	ArrayList<Organizer> findByPassword(String password);
	Organizer findByLoginAndPassword(String login, String password);

}
