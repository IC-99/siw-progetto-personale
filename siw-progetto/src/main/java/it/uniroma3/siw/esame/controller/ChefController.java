package it.uniroma3.siw.esame.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.esame.model.Buffet;
import it.uniroma3.siw.esame.model.Chef;
import it.uniroma3.siw.esame.service.BuffetService;
import it.uniroma3.siw.esame.service.ChefService;
import it.uniroma3.siw.esame.validator.ChefValidator;

@Controller
public class ChefController {
	
	@Autowired 
	private ChefService chefService;

	@Autowired
	private ChefValidator chefValidator;
	
	@Autowired 
	private BuffetService buffetService;
	
	//ADMIN
	
	//aggiunge uno chef
	@PostMapping("/admin/chef")
	public String addChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(chef, bindingResult);

		if(!bindingResult.hasErrors()) {
			chefService.save(chef);
			model.addAttribute("chef", chef);
			return "admin/chef.html";
		}
		model.addAttribute("fromBuffet", false);
		return "admin/chefForm.html";
	}

	//aggiunge lo chef al buffet
	@PostMapping("/admin/buffet/{buffet_id}/chef")
	public String addChef(@PathVariable("buffet_id") Long buffet_id, @Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {

		if(!bindingResult.hasErrors()) {
			Buffet buffet = buffetService.findById(buffet_id);
			
			if(chefService.alreadyExists(chef)) {
				Chef chefNelSistema = chefService.findByNomeCognomeNazionalita(chef);
				buffet.setChef(chefNelSistema);
				chefService.save(chefNelSistema);
			}
			else {
				buffet.setChef(chef);
				chefService.save(chef);
			}
			model.addAttribute("buffet", buffet);
			return "admin/buffet.html";
		}
		model.addAttribute("buffet", buffetService.findById(buffet_id));
		model.addAttribute("fromBuffet", true);
		return "admin/chefForm.html";
	}

	//visualizza tutti gli chef nel sistema
	@GetMapping("/admin/chefs")
	public String getChefs(Model model) {
		List<Chef> chefs = chefService.findAll();
		model.addAttribute("chefs", chefs);
		return "admin/chefs.html";
	}

	//visualizza uno chef presente nel sistema
	@GetMapping("/admin/chef/{chef_id}")
	public String getChef(@PathVariable("chef_id") Long chef_id, Model model) {
		Chef chef = chefService.findById(chef_id);
		model.addAttribute("chef", chef);
		return "admin/chef.html";
	}

	//visualizza il form per l'inserimento di uno chef
	@GetMapping("/admin/chefForm")
	public String getBuffetForm(Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("fromBuffet", false);
		return "admin/chefForm.html";
	}
	
	//visualizza il form per l'inserimento dello chef nel buffet
	@GetMapping("/admin/buffet/{buffet_id}/chefForm")
	public String getChefForm(@PathVariable("buffet_id") Long buffet_id, Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("buffet", buffetService.findById(buffet_id));
		model.addAttribute("fromBuffet", true);
		return "admin/chefForm.html";
	}
	
	//visualizza la pagina di conferma rimozione dello chef dal sistema
	@GetMapping("/admin/toDeleteChef/{chef_id}")
	public String toDeleteChef(@PathVariable("chef_id") Long chef_id, Model model) {
		model.addAttribute("chef", chefService.findById(chef_id));
		return "admin/confermaRimozioneChef.html";
	}

	//elimina lo chef dal buffet e dal sistema, ritorna alla pagina del buffet
	@GetMapping("/admin/deleteChef/{chef_id}")
	public String deleteChef(@PathVariable("chef_id") Long chef_id, Model model) {
		chefService.deleteById(chef_id);
		return this.getChefs(model);
	}
	
	//USER
	
	//visualizza tutti gli chef nel sistema
	@GetMapping("/chefs")
	public String getChefsUser(Model model) {
		List<Chef> chefs = chefService.findAll();
		model.addAttribute("chefs", chefs);
		return "user/chefs.html";
	}
	
	//visualizza uno chef presente nel sistema
	@GetMapping("/chef/{chef_id}")
	public String getChefUser(@PathVariable("chef_id") Long chef_id, Model model) {
		Chef chef = chefService.findById(chef_id);
		model.addAttribute("chef", chef);
		return "user/chef.html";
	}

}
