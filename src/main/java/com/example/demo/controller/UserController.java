package com.example.demo.controller;

import java.util.ArrayList;

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
import com.example.demo.model.Organizer;
import com.example.demo.model.Results;
import com.example.demo.model.User;
import com.example.demo.model.enumerator.Gender;
import com.example.demo.repo.MarathonRepo;
import com.example.demo.repo.ResultsRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.services.MarathonServiceImpl;
import com.example.demo.services.OrganizerServiceImpl;
import com.example.demo.services.UserServiceImpl;

@Controller
@RequestMapping(value="/u")
public class UserController {
	
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	MarathonServiceImpl marathonServiceImpl;  
	@Autowired
	UserRepo userRepo;
	@Autowired
	MarathonRepo marathonRepo;
	@Autowired
	ResultsRepo resultRepo;
	
	
	@GetMapping(value="/signup")
	public String registerUserGet(User user) {
		return "signup";
	}
	
	@PostMapping(value = "/signup")
	public String registerUserPost(@Valid User user, BindingResult result) {
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
		userServiceImpl.addParticipantToMarathon(usr_id, mar_id);
		return "redirect:/u/my-marathons/{usr_id}";
	}
	
	@GetMapping(value="/my-marathons/{usr_id}")
	public String viewUserMarathons(@PathVariable(name = "usr_id") long usr_id, Model model) {
		model.addAttribute("myMarathons", userServiceImpl.findMyMarathons(usr_id));
		return "my-marathons";
	}
	
	@GetMapping(value="/marathon-view")
	public String marathonView(User user, Model model) {
		model.addAttribute("allMarathons", marathonServiceImpl.findAllMarathons());
		return "marathon-view";
	}
	
	@GetMapping(value="/history-view/{id}")
	public String historyView(@PathVariable(name = "id") long id, Model model) {
		model.addAttribute("allMarathons", marathonServiceImpl.findAllMarathons());
		return"marathon-view";
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
		
		userServiceImpl.addParticipantToMarathon(u1.getID_usr(), m1.getId());
		userServiceImpl.addParticipantToMarathon(u1.getID_usr(), m3.getId());
		userServiceImpl.addParticipantToMarathon(u2.getID_usr(), m1.getId());
		userServiceImpl.addParticipantToMarathon(u2.getID_usr(), m2.getId());
		
		resultRepo.save(new Results(u1, m1, false, "13:00"));
		resultRepo.save(new Results (u1, m3, true, "12:30"));
		resultRepo.save(new Results (u2, m1, false, "14:00"));
		resultRepo.save(new Results (u2, m2, false, "13:30"));
		
		return "redirect:/u/marathon-view";
	}
	
	@GetMapping(value = "/results/{usr_id}")
	public String resultsAllUsersGet(@PathVariable(name = "usr_id") long usr_id, Model model,User user) 
	{
		
		model.addAttribute("marathons", userServiceImpl.findMyMarathons(usr_id));
		return "results";
	}
	@PostMapping(value="/results/{usr_id}")
	public String resultsAllUsersPost(@PathVariable(name = "usr_id") long usr_id, User user) 
	{
		System.out.println(user.getMarathons().size());
		ArrayList<Marathon> tempList =new ArrayList<Marathon>();
		for(Marathon m:user.getMarathons())
		{
			tempList.add(m);
		}
		userServiceImpl.findResultsForMarathon(tempList.get(0).getId());
		return "redirect:/u/results/{usr_id}/"+tempList.get(0).getId();
	}
	
	@GetMapping(value = "/results/{usr_id}/{mar_id}")
	public String resultsAllUsersGet(@PathVariable(name = "usr_id") long usr_id,@PathVariable(name = "mar_id") long mar_id, Model model) 
	{
		
		model.addAttribute("allResults", userServiceImpl.findResultsForMarathon(mar_id));
		return "result-view";
	}
	
	
	
}


