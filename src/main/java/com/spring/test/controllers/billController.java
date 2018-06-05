package com.spring.test.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.test.models.entity.Bill;
import com.spring.test.models.entity.Client;
import com.spring.test.models.entity.ItemBill;
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
	
	@PostMapping("/form")
	public String store(Bill bill, 
			@RequestParam(name="item_id[]", required= false) Long[] itemId,
			@RequestParam(name="quantity[]", required= false) Integer[] quantity,
			RedirectAttributes flash,
			SessionStatus status){
		
		for(int i=0; i < itemId.length ; i++) {
			Product product = clientService.findProductById(itemId[i]);
			
			ItemBill line = new ItemBill();
			line.setQuantity(quantity[i]);
			line.setProduct(product);
			bill.addItemBill(line);
		}
		
		clientService.saveBill(bill);
		status.setComplete();
		
		flash.addFlashAttribute("success", "Factura creada con éxito");
		
		return "redirect:/show/" + bill.getClient().getId();
	}
	
}
