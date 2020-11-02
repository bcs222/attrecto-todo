package com.attrecto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcStartController {

	@GetMapping("/test")
	public String test() {
		
		return "index";
	}
}
