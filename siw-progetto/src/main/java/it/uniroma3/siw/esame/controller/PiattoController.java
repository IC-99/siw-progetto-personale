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
import it.uniroma3.siw.esame.model.Piatto;
import it.uniroma3.siw.esame.service.BuffetService;
import it.uniroma3.siw.esame.service.PiattoService;
import it.uniroma3.siw.esame.validator.PiattoValidator;

@Controller
public class PiattoController {

	@Autowired 
	private PiattoService piattoService;

	@Autowired
	private PiattoValidator piattoValidator;

	@Autowired 
	private BuffetService buffetService;
	
	@Autowired 
	private BuffetController buffetController;
	
	//ADMIN

	//aggiunge un piatto a un buffet
	@PostMapping("/admin/buffet/{buffet_id}/piatto")
	public String addPiatto(@PathVariable("buffet_id") Long buffet_id, @Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResult, Model model) {
		this.piattoValidator.validate(piatto, bindingResult);

		if(!bindingResult.hasErrors()) {
			Buffet buffet = buffetService.findById(buffet_id);
			buffet.addPiatto(piatto);
			piattoService.save(piatto);
			model.addAttribute("buffet", buffet);
			return "admin/buffet.html";
		}
		model.addAttribute("buffet", buffetService.findById(buffet_id));
		return "admin/piattoForm.html";
	}

	//visualizza tutti i piatti nel sistema
	@GetMapping("/admin/piatti")
	public String getPiatti(Model model) {
		List<Piatto> piatti = piattoService.findAll();
		model.addAttribute("piatti", piatti);
		return "admin/piatti.html";
	}

	//visualizza un piatto dal sistema
	@GetMapping("/admin/piatto/{piatto_id}")
	public String getPiatto(@PathVariable("piatto_id") Long piatto_id, Model model) {
		Piatto piatto = piattoService.findById(piatto_id);
		model.addAttribute("piatto", piatto);
		return "admin/piatto.html";
	}

	//visualizza il form per l'inserimento di un piatto nel buffet
	@GetMapping("/admin/buffet/{buffet_id}/piattoForm")
	public String getPiattoForm(@PathVariable("buffet_id") Long buffet_id, Model model) {
		model.addAttribute("piatto", new Piatto());
		model.addAttribute("buffet", buffetService.findById(buffet_id));
		return "admin/piattoForm.html";
	}

	//visualizza la pagina di conferma rimozione di un piatto dal buffet
	@GetMapping("/admin/buffet/{buffet_id}/toDeletePiatto/{piatto_id}")
	public String toDeletePiatto(@PathVariable("buffet_id") Long buffet_id, @PathVariable("piatto_id") Long piatto_id, Model model) {
		model.addAttribute("piatto", piattoService.findById(piatto_id));
		model.addAttribute("buffet", buffetService.findById(buffet_id));
		return "admin/confermaRimozionePiatto.html";
	}

	//elimina il piatto dal buffet e dal sistema, ritorna alla pagina del buffet
	@GetMapping("/admin/buffet/{buffet_id}/deletePiatto/{piatto_id}")
	public String deletePiatto(@PathVariable("buffet_id") Long buffet_id, @PathVariable("piatto_id") Long piatto_id, Model model) {
		Buffet buffet = buffetService.findById(buffet_id);
		Piatto piatto = piattoService.findById(piatto_id);
		buffet.removePiatto(piatto);
		piattoService.deleteById(piatto_id);
		return this.buffetController.getBuffet(buffet_id, model);
	}
	
	//USER
	
	//visualizza un piatto dal sistema
	@GetMapping("/piatto/{piatto_id}")
	public String getPiattoUser(@PathVariable("piatto_id") Long piatto_id, Model model) {
		Piatto piatto = piattoService.findById(piatto_id);
		model.addAttribute("piatto", piatto);
		return "user/piatto.html";
	}
}
