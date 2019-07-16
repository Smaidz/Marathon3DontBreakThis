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
import com.example.demo.model.Organizer;
import com.example.demo.model.User;
import com.example.demo.services.AdminService;
import com.example.demo.services.AdminServiceIMPL;
import com.example.demo.services.MarathonService;
import com.example.demo.services.MarathonServiceImpl;
import com.example.demo.services.OrganizerService;
import com.example.demo.services.OrganizerServiceImpl;
import com.example.demo.services.ResultService;
import com.example.demo.services.ResultServiceImpl;
import com.example.demo.services.UserService;
import com.example.demo.services.UserServiceImpl;

@Controller
@RequestMapping(value = "/a")
public class AdminController {
	
	@Autowired
	AdminServiceIMPL adminServiceImpl;
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	MarathonServiceImpl marathonServiceImpl;
	@Autowired
	ResultServiceImpl resultServiceImpl;
	
	@GetMapping(value="/add")
	public String add() {
		adminServiceImpl.uploadTestData();
		return "redirect:/a/adm-auth";
	}
	
	@GetMapping(value = "/view-org")
	public String vieworg(Model model) {
		model.addAttribute("object", adminServiceImpl.selectAll());
	return "view-org";
	}
	
	@GetMapping(value = "/add-org")
	public String addorgGet(Organizer organizer)
	{
		return "add-org";
	}
	
	@PostMapping(value = "/add-org")
	public String addorgPost(@Valid Organizer organizer, BindingResult result) {
		if (organizerServiceImpl.organizerAlreadyExists(organizer)) {
			return "add-org";
		} else if(result.hasErrors())
			return "add-org";
		else {
			organizerServiceImpl.addNewOrganizer(organizer);
			return "redirect:/a/view-org";
		}
	}
	@GetMapping(value = "/update-org/{id_org}")
	public String updateorgGet(@PathVariable(name ="id_org") long id_org, Model model) {
		model.addAttribute("organizer", organizerServiceImpl.selectById_org(id_org));
		return "update-org";
	}
	@PostMapping(value = "/update-org/{id_org}")
	public String updateorgPost(@PathVariable(name="id_org") long id_org, Organizer organizer)
	{
		organizerServiceImpl.updateOrganizerById_org(organizer, id_org);
		return "redirect:/a/view-org";
	}
	@GetMapping(value = "/delete/{id_org}")
	public String deleteorgGet(@PathVariable(name="id_org") long id_org) {
		organizerServiceImpl.deleteOrganizerById_org(id_org);
		return "redirect:/a/view-org";
	}
	@GetMapping(value="/add-marathon")
	public String addNewCar(Marathon marathon) {
		return "add-marathon";
	}
	
	@GetMapping(value="/adm-auth")
	public String authoriseAdminGet(Organizer organizer) {
		return "adm-auth";
	}
	
	@PostMapping(value = "/adm-auth")
	public String authoriseAdminPost(@Valid Organizer organizer, BindingResult result) {
		
		long id = organizerServiceImpl.findByLoginAndPassword(organizer).getId_org();
		
		return "redirect:/a/adminhome/"+id;
	}
	
	@GetMapping(value="/adminhome/{id_adm}")
	public String adminHomeGet(@PathVariable(name = "id_adm") long id_adm, Model model) {
		model.addAttribute("org", organizerServiceImpl.selectById_org(id_adm));
		return "adminhome";
	}
	
	@GetMapping(value="/all-users")
	public String marathonView(Model model) {
		model.addAttribute("allUsers", userServiceImpl.selectAllUsers());
		return "all-users";
	}
	
	@GetMapping(value="/update-user-admin/{id}")
	public String updateUser(@PathVariable(name="id")long id, Model model) {
		model.addAttribute("user", userServiceImpl.findByID(id));
		return "update-user-admin";
	}
	
	@PostMapping(value="/update-user-admin/{id}")
	public String updateUserPost(@PathVariable(name="id")long id, User user) {
		adminServiceImpl.adminUpdateUserById(user, id);
		return "redirect:/a/all-users/";
	}
	
	@GetMapping(value="/delete-user-admin/{id}")
	public String DeleteUser(@PathVariable(name="id")long id) {
		userServiceImpl.deleteUserByID(id);
		return "redirect:/a/all-users";
	}
	
	@GetMapping(value="/marathon-view-admin")
	public String marathonViewAdmin(Model model) {
		model.addAttribute("allMarathons", marathonServiceImpl.findAllMarathons());
		return "marathon-view-admin";
	}
	
	@GetMapping(value="/delete-marathon/{id}")
	public String DeleteMarathon(@PathVariable(name="id")long id) {
		marathonServiceImpl.deleteMarathonByID(id);
		return "redirect:/a/marathon-view-admin";
	}
	
	@GetMapping(value="/results-view-admin")
	public String viewAllResults(Model model) {
		model.addAttribute("allResults", adminServiceImpl.getAllResults());
		return "results-view-admin";
	}
	
	@GetMapping(value="/delete-result/{id}")
	public String DeleteResult(@PathVariable(name="id")long id) {
		resultServiceImpl.deleteById_res(id);
		return "redirect:/a/results-view-admin";
	}

}