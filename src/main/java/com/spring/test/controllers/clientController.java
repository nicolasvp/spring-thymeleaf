package com.spring.test.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.spring.test.models.entity.Client;
import com.spring.test.models.service.IClientService;

@Controller
@SessionAttributes("client")
public class clientController {
	
	@Autowired
	private IClientService clientService;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("title","Listado de clientes");
		model.addAttribute("clients",clientService.findAll());
		return "index";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String create(Map<String, Object> model) {
		Client client = new Client();
		model.put("client",client);
		model.put("title", "Formulario para crear un cliente");
		return "create";
	}
	
	// Guardar y actualizar
	@RequestMapping(value="/store",method=RequestMethod.POST)
	public String store(@Valid Client client, BindingResult result, Model model, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Formulario para un cliente");
			return "create";
		}
		clientService.save(client);
		status.setComplete();
		return "redirect:/index";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable(value="id") Long id,Map<String, Object> model) {
		Client client = null;
		
		if(id > 0) {
			client = clientService.findOne(id);
		}
		else {
			return "redirect:index";
		}
		
		model.put("client",client);
		model.put("title", "Formulario para editar un cliente");
		return "create";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String destroy(@PathVariable(value="id") Long id) {
		if(id > 0) {
			clientService.delete(id);
		}
		return "redirect:/index";
	}
}
