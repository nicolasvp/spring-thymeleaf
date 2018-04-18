package com.spring.test.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.test.models.entity.Client;
import com.spring.test.models.service.IClientService;
import com.spring.test.util.paginator.PageRender;

@Controller
@SessionAttributes("client")
public class clientController {
	
	@Autowired
	private IClientService clientService;
	
	// Debugger
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(@RequestParam(name="page", defaultValue="0") int page,Model model) {
		
		Pageable pageRequest = new PageRequest(page,5);
		
		Page<Client> clients = clientService.findAll(pageRequest);
		
		PageRender<Client> pageRender = new PageRender<>("/",clients);
		
		model.addAttribute("title","Listado de clientes");
		model.addAttribute("clients",clients);
		model.addAttribute("page",pageRender);
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
	public String store(@Valid Client client, BindingResult result, Model model, @RequestParam("file") MultipartFile photo,RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("title", "Formulario para un cliente");
			return "create";
		}
		
		if(!photo.isEmpty()) {
			
			// Elimina foto
			if(client.getId() != null && client.getId() > 0 && client.getPhoto() != null && client.getPhoto().length() > 0) {
				Path rootPath = Paths.get("uploads").resolve(client.getPhoto()).toAbsolutePath();
				File file = rootPath.toFile();
				
				if(file.exists() && file.canRead()) {
					file.delete();
				}
			}
			
			String uniqueName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
			Path rootPath = Paths.get("uploads").resolve(uniqueName);
			Path rootAbsolutePath = rootPath.toAbsolutePath();
			
			// Debugg de las rutas para ver la diferencia
			log.info("rootPath: "+ rootPath);
			log.info("rootAbsolutePath: "+ rootAbsolutePath);

			try {
				Files.copy(photo.getInputStream(),rootAbsolutePath);
				flash.addFlashAttribute("info","Se ha subido el archivo"+ uniqueName +" correctamente!");
				client.setPhoto(uniqueName);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String flashMessage = (client.getId() != null) ? "El registro se ha editado exitosamente!" : "El registro se ha creado exitosamente!";
		
		clientService.save(client);
		status.setComplete();
		flash.addFlashAttribute("success", flashMessage);
		return "redirect:/";
	}
	
	@RequestMapping(value="/edit/{id}")
	public String edit(@PathVariable(value="id") Long id,Map<String, Object> model, RedirectAttributes flash) {
		Client client = null;
		
		if(id > 0) {
			client = clientService.findOne(id);
			if(client == null) {
				flash.addFlashAttribute("error", "El registro no existe en la base de datos");
				return "redirect:/";
			}
		}
		else {
			flash.addFlashAttribute("error", "ID no válido");
			return "redirect:/";
		}
		
		model.put("client",client);
		model.put("title", "Formulario para editar un cliente");
		return "create";
	}
	
	@RequestMapping(value="/show/{id}")
	public String show(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Client client = clientService.findOne(id);
		
		if(client == null) {
			flash.addFlashAttribute("error","No se encuentra el registro en la base de datos");
			return "return:redirect:/";
		}
		
		model.put("client",client);
		model.put("titulo","Detalle cliente: " + client.getName());
		
		return "show";
	}
	
	
	@RequestMapping(value="/delete/{id}")
	public String destroy(@PathVariable(value="id") Long id, RedirectAttributes flash) {
		if(id > 0) {
			Client client = clientService.findOne(id);
			
			clientService.delete(id);
			flash.addFlashAttribute("success", "El registro se ha eliminado exitosamente!");
			
			Path rootPath = Paths.get("uploads").resolve(client.getPhoto()).toAbsolutePath();
			File file = rootPath.toFile();
			
			if(file.exists() && file.canRead()) {
				if(file.delete()) {
					flash.addFlashAttribute("info","La foto del cliente "+ client.getName() +" correctamente!");
				}
			}
		}
		return "redirect:/";
	}
}
