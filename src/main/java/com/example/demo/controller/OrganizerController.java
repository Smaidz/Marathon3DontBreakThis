package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;

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
import com.example.demo.model.Results;
import com.example.demo.model.User;
import com.example.demo.model.enumerator.Gender;
import com.example.demo.services.MarathonServiceImpl;
import com.example.demo.services.OrganizerServiceImpl;
import com.example.demo.services.UserServiceImpl;

@Controller
@RequestMapping(value="/o")
public class OrganizerController {
	
	@Autowired
	OrganizerServiceImpl organizerServiceImpl;
	@Autowired
	UserServiceImpl userServiceImpl;
	@Autowired
	MarathonServiceImpl marathonServiceImpl;
	
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
	
	
	@GetMapping(value = "/export-data/{id}")
	public String exportDataGet(@PathVariable(name="id")long id, Model model,Organizer organizer) {
		ArrayList<Marathon> tempList=organizerServiceImpl.findMarathonByOrganizerById(id);
		
		model.addAttribute("marathons",tempList );
		return "export-data";
	}
	@PostMapping(value="/export-data/{id}")
	public String exportDataGet(@PathVariable(name="id")long id,Organizer organizer) {
		System.out.println(organizer.getMarathons().size());
		
	
		ArrayList<Marathon> tempList=new ArrayList<>();
		for(Marathon m:organizer.getMarathons())
		{
			tempList.add(m);
			System.out.println(m.getId());
		}
		if(organizer.getMarathons().size()==1)
			organizerServiceImpl.exportOneMarathonExcel(tempList.get(0).getId());
		if(organizer.getMarathons().size()>1)
		{
				organizerServiceImpl.exportMarathonsExcel(id, tempList);
		}
		
		return "export-data-ok";
	}
	

}