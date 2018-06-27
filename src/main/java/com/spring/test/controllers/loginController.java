package com.spring.test.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class loginController {
	
	@GetMapping("/login")
	public String login(@RequestParam(value="error", required=false) String error, 
			@RequestParam(value="logout", required=false) String logout,
			Model model, 
			Principal principal, 
			RedirectAttributes flash) {
		
		// Evita el doble inicio de sesión redirigiendo al index
		if(principal != null) {
			flash.addFlashAttribute("info", "Su sesión aún se encuentra activa");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Las credenciales ingresadas no coinciden con nuestros registros");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado la sesión correctamente");
		}
		
		return "login";
	}
}
