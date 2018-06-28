package com.spring.test.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

@PreAuthorize("hasRole('admin')")
@Controller
@RequestMapping("/bill")
@SessionAttributes("bill")
public class billController {
	
	@Autowired
	private IClientService clientService;
	
	@GetMapping("/show/{id}")
	public String show(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Bill bill = clientService.fetchByIdWithClientWithBillItemWithProduct(id);	//clientService.findBillById(id);
		
		if(bill == null) {
			flash.addFlashAttribute("error","No se encuentra el registro en la base de datos");
			return "return:redirect:/";
		}
		
		model.put("bill", bill);
		model.put("title","Factura: ".concat(bill.getDescription()));
		return "bill/show";
	}
	
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
	public String store(@Valid Bill bill, 
			BindingResult result,
			Model model,
			@RequestParam(name="item_id[]", required= false) Long[] itemId,
			@RequestParam(name="quantity[]", required= false) Integer[] quantity,
			RedirectAttributes flash,
			SessionStatus status){
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Crear Factura");
			return "bill/form";
		}
		
		if(itemId == null || itemId.length == 0) {
			model.addAttribute("title", "Crear Factura");
			model.addAttribute("error", "Error: La factura debe tener al menos 1 linea");
			return "bill/form";
		}
		
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
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		Bill bill = clientService.findBillById(id);
		
		if(bill != null) {
			clientService.deleteBill(id);
			flash.addFlashAttribute("success", "Factura eliminada con éxito");
			return "redirect:/show/" + bill.getClient().getId();
		}
		
		flash.addFlashAttribute("error", "La factura no existe, no se pudo eliminar");
		return "redirect:/";
	}
	
}
