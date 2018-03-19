package com.spring.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {

	@GetMapping("/")
	public String hello(Model model) {
		model.addAttribute("message","Hola pal que lee!");
		return "index";
	}
}
