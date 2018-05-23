package com.spring.test.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.test.models.entity.Bill;
import com.spring.test.models.entity.Client;
import com.spring.test.models.entity.Product;
import com.spring.test.models.service.IClientService;

@Controller
@RequestMapping("/bill")
@SessionAttributes("bill")
public class billController {
	
	@Autowired
	private IClientService clientService;
	
	@GetMapping("/form/{clientId}")
	public String create(@PathVariable(value="clientId") Long clientId, Map<String, Object> model, RedirectAttributes flash) {
		
		Client client = clientService.findOne(clientId);
		
		if(client == null) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/";
		}
		
		Bill bill = new Bill();
		bill.setClient(client);
		
		model.put("bill", bill);
		model.put("title", "Crear factura");
		
		return "bill/form";
	}
	
	@GetMapping(value="/get-products/{term}", produces= {"application/json"})
	public @ResponseBody List<Product> loadProducts(@PathVariable String term){
		return clientService.findByName(term);
	}
	
}
