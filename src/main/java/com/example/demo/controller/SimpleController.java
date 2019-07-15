package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Results;
import com.example.demo.services.UserServiceImpl;


@Controller
@RequestMapping(value="")
public class SimpleController {
	@Autowired
	
	@GetMapping(value="")
	public String homePage() {
;
		return "home";
	}
	
}
