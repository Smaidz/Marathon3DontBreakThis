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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Marathon;
import com.example.demo.model.User;
import com.example.demo.model.enumerator.Gender;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.services.MarathonServiceImpl;
import com.example.demo.services.UserServiceImpl;

@Controller
@RequestMapping(value="/u")
public class UserController {
	
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	MarathonServiceImpl marathonServiceImpl;  
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
	
	@GetMapping(value="/marathon-view/{usr_id}")
	public String marathonViewAuthorised(@PathVariable(name = "usr_id") long usr_id, Model model, Marathon marathon) {
		model.addAttribute("allMarathons", marathonServiceImpl.findAllMarathons());
		return"marathon-view";
	}
	
	@PostMapping(value="/marathon-view/{usr_id}/{mar_id}")
	public String participateInMarathon(@PathVariable(name = "usr_id") long usr_id, @PathVariable(name = "mar_id") long mar_id) {
		System.out.println("CONTR-UUUUUUUUUUUUUUU"+usr_id +" MMMMMMMMMMMMMMMMMMMM" + mar_id);
		
		userServiceImpl.addParticipantToMarathon(usr_id, mar_id);
		return "redirect:/u/my-marathons/{usr_id}";
	}
	
	@GetMapping(value="/my-marathons/{usr_id}")
	public String viewUserMarathons(@PathVariable(name = "usr_id") long usr_id, Model model) {
		System.out.println("MY MARATHONS USR ID" +usr_id);
		model.addAttribute("myMarathons", userServiceImpl.findMyMarathons(usr_id));
		return "my-marathons";
	}
	
	
	
	@GetMapping(value="/marathon-view")
	public String marathonView(User user, Model model) {
		model.addAttribute("allMarathons", marathonServiceImpl.findAllMarathons());
		return "marathon-view";
	}
	
	
	
	@GetMapping(value="/add")
	public String add(Model model) {

		Marathon m1 = new Marathon("Ventspils Skrien", 40, "Ventspils", "01.01.2019", 1200);
		Marathon m2 = new Marathon("Rigas pusmaratons", 20, "Riga", "01.01.2019", 1200);
		Marathon m3 = new Marathon("Lielais skrejiens", 40, "Ventspils", "01.01.2019", 1200);
		
		User u1 = new User("Janis", "Berzins", "email@domain.com", "password", "14.04.1991", Gender.Male);
		User u2 = new User("Liene", "Liepa", "email@pasts.lv", "password", "21.12.1993", Gender.Female);
		

		userRepo.save(u1);
		userRepo.save(u2);

		marathonRepo.save(m1);
		marathonRepo.save(m2);
		marathonRepo.save(m3);
		
		long id = u2.getID_usr(); 
		System.out.println(id + "" + m3.toString());
		userServiceImpl.addParticipantToMarathon(id, m3.getId());
		
		return "redirect:/u/marathon-view";
	}
	
	
}


