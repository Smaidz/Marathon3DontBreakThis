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
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Marathon;
import com.example.demo.model.Results;
import com.example.demo.services.OrganizerServiceImpl;
import com.example.demo.services.UserServiceImpl;

@Controller
@RequestMapping(value="/o", method=RequestMethod.GET)
public class OrganizerController {
	
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	
	@Autowired
	UserServiceImpl userServiceImpl;
	
	
	
	
	@GetMapping(value="	/add-result")
	public String addResult(Marathon marathon, Model model) {
		model.addAttribute("allUsers", organizerServiceImpl.selectAllUsers());
		model.addAttribute("allMarathons", userServiceImpl.findAllMarathons());
		return "add-result";
	}
	
	@PostMapping(value="/add-result")
	public String addNewResultPost(@Valid Results results, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors())
			return "/add-result";
		
		organizerServiceImpl.insertNewResult(results);
		return "add-result";
	}
	
	
	
	
	@GetMapping(value="/add-marathon")
	public String addNewCar(Marathon marathon) {
		return "add-marathon";
	}
	
	@PostMapping(value="/add-marathon")
	public String addNewCarPost(@Valid Marathon marathon, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors())
			return "add-marathon";
		
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
}