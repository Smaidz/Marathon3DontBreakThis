package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Marathon;
import com.example.demo.model.Results;
import com.example.demo.model.User;
import com.example.demo.model.enumerator.Gender;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.ResultsRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.services.UserServiceImpl;

@Controller
@RequestMapping(value="/u")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	UserRepo userRepo;
	@Autowired
	MarathonRepo marathonRepo;
	@Autowired
	ResultsRepo resultRepo;
	
	@GetMapping(value="/history-view/{id}")
	public String historyView(@PathVariable(name = "id") long id, Model model) {
		model.addAttribute("myResult", userServiceImpl.selectByUserIdRes(id));
		return"history-view";
	}
	
	@GetMapping(value="/signup")
	public String registerUserGet(User user) {
		return "signup";
	}
	
	@PostMapping(value = "/signup")
	public String registerUserPost(@Valid User user, BindingResult result) {
		System.out.println(user.toString());
		if(result.hasErrors()) 
			return "signup";
		else {
			userServiceImpl.addNewUser(user);
			return "redirect:/u/authpage";
		}
	}
	
	@GetMapping(value="/authpage")
	public String authoriseUserGet(User user) {
		return "authpage";
	}
	
	@PostMapping(value = "/authpage")
	public String authoriseUserPost(@Valid User user, BindingResult result) {
		
		long id = userServiceImpl.findByEmailAndPassword(user).getID_usr();
		
		return "redirect:/u/marathon-view/"+id;
	}
	
	@GetMapping(value="/marathon-view/{id}")
	public String marathonViewAuthorised(@PathVariable(name = "id") long id, Model model) {
		model.addAttribute("allMarathons", userServiceImpl.findAllMarathons());
		return"marathon-view";
	}
	
	
	
	@PostMapping(value="/marathon-view/{id}")
	public String participateInMarathon() {
		
		return "";
	}
	
	
	
	
	
	@GetMapping(value="/marathon-view")
	public String marathonView(User user, Model model) {
		model.addAttribute("allMarathons", userServiceImpl.findAllMarathons());
		return "marathon-view";
	}
	
	@GetMapping(value="/add")
	public String add(Model model) {

		Marathon m1 = new Marathon("Riga Skrien", 40, "Ventspils", "01.01.2019", "12:00");
		Marathon m2 = new Marathon("Ventspils Skrien", 50, "Ventspils", "01.01.2019", "12:00");
		Marathon m3 = new Marathon("Talsi Skrien", 45, "Ventspils", "01.01.2019", "12:00");
		
		User u1 = new User("Janis", "Berzins", "email@domain.com", "password", "14.04.1991", Gender.Male);
		User u2 = new User("Liene", "Liepa", "email@pasts.lv", "password", "21.12.1993", Gender.Female);
;
		userRepo.save(u1);
		userRepo.save(u2);

		marathonRepo.save(m1);
		marathonRepo.save(m2);
		marathonRepo.save(m3);
		
		resultRepo.save(new Results(u1, m1, false, "13:00"));
		resultRepo.save(new Results (u1, m3, true, "12:30"));
		resultRepo.save(new Results (u2, m1, false, "14:00"));
		resultRepo.save(new Results (u2, m2, false, "13:30"));
		
		return "redirect:/u/marathon-view";
	}
}


