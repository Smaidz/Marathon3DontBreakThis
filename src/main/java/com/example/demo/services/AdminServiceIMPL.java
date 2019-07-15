package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Marathon;
import com.example.demo.model.Organizer;
import com.example.demo.model.Results;
import com.example.demo.model.User;
import com.example.demo.model.enumerator.Gender;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.OrganizerRepo;
import com.example.demo.repo.ResultsRepo;
import com.example.demo.repo.UserRepo;

@Service
public class AdminServiceIMPL implements AdminService{

	@Autowired
	UserServiceImpl userServiceImpl; 
	@Autowired
	OrganizerRepo organizerRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	MarathonRepo marathonRepo;
	@Autowired
	ResultsRepo resultRepo;

	
	@Override
	public ArrayList<Organizer> selectAll() {
		ArrayList<Organizer> tempList = new ArrayList<Organizer>();
		for (Organizer o:organizerRepo.findAll()) {
			if(!o.getAdmin())
			tempList.add(o);
		}
		return tempList;
	}

	@Override
	public void uploadTestData() {
		
		
		Organizer admin = new Organizer("Administrator", "AdminLogin", "AdminPass", "admin@pasts.ru");
		admin.setAdmin();
		admin.setFirstLogin(false);
		organizerRepo.save(admin);

			Marathon m1 = new Marathon("Ventspils Skrien", 40, "Ventspils", "01.01.2019", "1200");
			Marathon m2 = new Marathon("Rigas pusmaratons", 20, "Riga", "01.01.2019", "1200");
			Marathon m3 = new Marathon("Lielais skrejiens", 40, "Ventspils", "01.01.2019", "1200");
			
			User u1 = new User("Janis", "Berzins", "email@domain.com", "password", "14.04.1991", Gender.Male, true);
			User u2 = new User("Liene", "Liepa", "email@pasts.lv", "password", "21.12.1993", Gender.Female, false);
			
			Organizer o1 = new Organizer("OrganizerABC", "OrgLogin1", "orgpassword", "orgemail@pasts.ru");
			
			organizerRepo.save(o1);
	
			userRepo.save(u1);
			userRepo.save(u2);
	
			marathonRepo.save(m1);
			marathonRepo.save(m2);
			marathonRepo.save(m3);
			
			userServiceImpl.addParticipantToMarathon(u1.getID_usr(), m1.getID_mar());
			userServiceImpl.addParticipantToMarathon(u1.getID_usr(), m3.getID_mar());
			userServiceImpl.addParticipantToMarathon(u2.getID_usr(), m1.getID_mar());
			userServiceImpl.addParticipantToMarathon(u2.getID_usr(), m2.getID_mar());
			
			resultRepo.save(new Results(u1, m1, false, "13:00"));
			resultRepo.save(new Results (u1, m3, true, "12:30"));
			resultRepo.save(new Results (u2, m1, false, "14:00"));
			resultRepo.save(new Results (u2, m2, false, "13:30"));
			

		
		
	}
}
