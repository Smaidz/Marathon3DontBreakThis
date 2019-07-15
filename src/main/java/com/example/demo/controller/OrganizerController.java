package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Organizer;
import com.example.demo.model.Marathon;
import com.example.demo.model.Results;
import com.example.demo.services.EmailServiceImpl;
import com.example.demo.services.MarathonServiceImpl;
import com.example.demo.services.OrganizerServiceImpl;
import com.example.demo.services.UserServiceImpl;
import com.example.demo.controller.EmailController;

@Controller
@RequestMapping(value="/o")
public class OrganizerController {
	
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	MarathonServiceImpl marathonServiceImpl;
	@Autowired 
	EmailServiceImpl emailServiceImpl;
	
	@GetMapping(value="/add-result")
	public String addResult(Results results, Model model) {
		model.addAttribute("users", organizerServiceImpl.selectAllUsers());
		model.addAttribute("marathons", marathonServiceImpl.findAllMarathons());
		return "add-result";
	}
	
	@PostMapping(value="/add-result")
	public String resultPost(@Valid Results results, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors())
			return "add-result";
		organizerServiceImpl.insertNewResult(results);
		return "/add-result";
	}
	
	@GetMapping(value="/add-marathon")
	public String addNewCar(Marathon marathon) {
		return "add-marathon";
	}
	
	@PostMapping(value="/add-marathon")
	public String addNewCarPost(@Valid Marathon marathon, BindingResult bindingResult, Organizer organizer) throws MessagingException, IOException {
		
		if(bindingResult.hasErrors())
			return "add-marathon";
		emailServiceImpl.sendToSubs(organizer);
		organizerServiceImpl.insertNewMarathon(marathon);
		return "redirect:/u/marathon-view";
	}
	@GetMapping(value="/update-marathon/{id}")
	public String updateCar(@PathVariable(name="id")long id, Model model) {
		model.addAttribute("marathon", organizerServiceImpl.selectById(id));
		return "update-marathon";
	}
	
	@PostMapping(value="/update-marathon/{id}")
	public String updateCarPost(@PathVariable(name="id")long id, Model model, Marathon marathon) {
		organizerServiceImpl.updateMarathonById(marathon, id);
		return "redirect:/u/marathon-view";
	}
	
	@GetMapping(value = "/export-data")
	public String exportData(Model model) {
		organizerServiceImpl.exportDataExcel();
		//model.addAttribute("object", );
	return "export-data";
	}
	
	@GetMapping(value = "/marathon-inform/{id_org}")
	public String showCheckboxGet(@PathVariable(name = "id_org") long id_org,Model model) {
	    ArrayList<Marathon> marListTemp = organizerServiceImpl.selectByOrganizer(id_org);
	    model.addAttribute("marathons", marListTemp);
	    return "marathon-inform";
	}
	@PostMapping(value = "/marathon-inform/{id_org}")
	public String showCheckboxPost(@PathVariable(name = "id_org") long id_org, Model model)
	{
	    boolean isChecked = false;
	    model.addAttribute("marathons", organizerServiceImpl.selectByOrganizer(id_org));
	    model.addAttribute("isChecked", isChecked);
	    if(isChecked = true)
	    {
	    	ArrayList<Marathon> marResult = new ArrayList<Marathon>();
	    	model.addAttribute("isChecked", isChecked);
	    	
	    	
	    	return "isOK";
	    }
	    
	    return "marathon-inform";	
	}
	
	
	
}