package com.attrecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StartTesterController {

	@GetMapping("/test")
	public String test() {
		
		return "index";
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.GET)
	public String authenticate() {
		
		return "loginPage";
	}
}
