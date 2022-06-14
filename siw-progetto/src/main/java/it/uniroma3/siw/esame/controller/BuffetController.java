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
import it.uniroma3.siw.esame.validator.BuffetValidator;

@Controller
public class BuffetController {

	@Autowired 
	private BuffetService buffetService;

	@Autowired
	private BuffetValidator buffetValidator;

	@Autowired 
	private ChefService chefService;
	
	//ADMIN

	//aggiunge un buffet
	@PostMapping("/admin/buffet")
	public String addBuffet(@Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult, Model model) {
		this.buffetValidator.validate(buffet, bindingResult);

		if(!bindingResult.hasErrors()) {
			buffetService.save(buffet);
			model.addAttribute("buffet", buffet);
			return "admin/buffet.html";
		}
		model.addAttribute("fromChef", false);
		return "admin/buffetForm.html";
	}

	//aggiunge un buffet allo chef
	@PostMapping("/admin/chef/{chef_id}/buffet")
	public String addBuffetFromChef(@PathVariable("chef_id") Long chef_id, @Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResult, Model model) {
		this.buffetValidator.validate(buffet, bindingResult);

		if(!bindingResult.hasErrors()) {
			Chef chef = chefService.findById(chef_id);
			chef.addBuffet(buffet);
			buffet.setChef(chef);
			buffetService.save(buffet);
			model.addAttribute("chef", chef);
			return "admin/chef.html";
		}
		model.addAttribute("chef", chefService.findById(chef_id));
		model.addAttribute("fromChef", true);
		return "admin/buffetForm.html";
	}

	//visualizza tutti i buffet (non specifico id)
	@GetMapping("/admin/buffets")
	public String getBuffets(Model model) {
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "admin/buffets.html";
	}

	//visualizza un buffet
	@GetMapping("/admin/buffet/{id}")
	public String getBuffet(@PathVariable("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		return "admin/buffet.html";
	}

	//visualizza il form per l'inserimento di un buffet
	@GetMapping("/admin/buffetForm")
	public String getBuffetForm(Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("fromChef", false);
		return "admin/buffetForm.html";
	}

	//visualizza il form per l'inserimento di un buffet dello chef
	@GetMapping("/admin/chef/{chef_id}/buffetForm")
	public String getBuffetForm(@PathVariable("chef_id") Long chef_id, Model model) {
		model.addAttribute("buffet", new Buffet());
		model.addAttribute("chef", chefService.findById(chef_id));
		model.addAttribute("fromChef", true);
		return "admin/buffetForm.html";
	}

	//visualizza la pagina di conferma rimozione di un buffet
	@GetMapping("/admin/toDeleteBuffet/{id}")
	public String toDeleteBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", buffetService.findById(id));
		return "admin/confermaRimozioneBuffet.html";
	}

	//elimina il buffet e ritorna alla lista di tutti i buffet
	@GetMapping("/admin/deleteBuffet/{id}")
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		buffetService.deleteById(id);
		return this.getBuffets(model);
	}
	
	//USER
	
	//visualizza tutti i buffet (non specifico id)
	@GetMapping("/buffets")
	public String getBuffetsUser(Model model) {
		List<Buffet> buffets = buffetService.findAll();
		model.addAttribute("buffets", buffets);
		return "user/buffets.html";
	}
	
	//visualizza un buffet
		@GetMapping("/buffet/{id}")
		public String getBuffetUser(@PathVariable("id") Long id, Model model) {
			Buffet buffet = buffetService.findById(id);
			model.addAttribute("buffet", buffet);
			return "user/buffet.html";
		}
}
