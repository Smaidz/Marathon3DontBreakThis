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
import com.example.demo.model.User;
import com.example.demo.model.enumerator.Gender;
import com.example.demo.repo.MarathonRepo;
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
	public String marathonViewAuthorised(@PathVariable(name = "id") long id, Model model, Marathon marathon) {
		model.addAttribute("allMarathons", userServiceImpl.findAllMarathons());
		return"marathon-view";
	}
	
	@PostMapping(value="/marathon-view/{id}")
	public String participateInMarathon(@PathVariable(name = "id") long usr_id, Marathon marathon) {
		System.out.println(usr_id +" " + marathon.getId());
		userServiceImpl.addParticipantToMarathon(usr_id,marathon);
		return "redirect:/u/my-marathons";
	}
	
	
	
	
	
	@GetMapping(value="/marathon-view")
	public String marathonView(User user, Model model) {
		model.addAttribute("allMarathons", userServiceImpl.findAllMarathons());
		return "marathon-view";
	}
	
	@GetMapping(value="/add")
	public String add(Model model) {

		Marathon m1 = new Marathon("Ventspils Skrien", 40, "Ventspils", "01.01.2019", 1200);
		Marathon m2 = new Marathon("Ventspils Skrien", 40, "Ventspils", "01.01.2019", 1200);
		Marathon m3 = new Marathon("Ventspils Skrien", 40, "Ventspils", "01.01.2019", 1200);
		
		User u1 = new User("Janis", "Berzins", "email@domain.com", "password", "14.04.1991", Gender.Male);
		User u2 = new User("Liene", "Liepa", "email@pasts.lv", "password", "21.12.1993", Gender.Female);
;
		userRepo.save(u1);
		userRepo.save(u2);

		marathonRepo.save(m1);
		marathonRepo.save(m2);
		marathonRepo.save(m3);
		return "redirect:/u/marathon-view";
	}
}


