package com.spring.test.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.test.models.dao.IClientDao;
import com.spring.test.models.entity.Client;

@Controller
public class clientController {
	
	@Autowired
	@Qualifier("clientDaoJPA")
	private IClientDao clientDao;
	
	@RequestMapping(value="/index", method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("title","Listado de clientes");
		model.addAttribute("clients",clientDao.findAll());
		return "index";
	}
	
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public String create(Map<String, Object> model) {
		Client client = new Client();
		model.put("client",client);
		model.put("title", "Formulario para un cliente");
		return "create";
	}
	
	@RequestMapping(value="/store",method=RequestMethod.POST)
	public String store(@Valid Client client, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Formulario para un cliente");
			return "create";
		}
		clientDao.save(client);
		return "redirect:index";
	}
}
