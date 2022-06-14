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

import it.uniroma3.siw.esame.model.Servizio;
import it.uniroma3.siw.esame.model.Stanza;
import it.uniroma3.siw.esame.service.ServizioService;
import it.uniroma3.siw.esame.service.StanzaService;
import it.uniroma3.siw.esame.validator.ServizioValidator;

@Controller
public class ServizioController {

	@Autowired 
	private ServizioService servizioService;

	@Autowired
	private ServizioValidator servizioValidator;

	@Autowired 
	private StanzaService stanzaService;

	@Autowired
	private StanzaController stanzaController;

	//ADMIN
	
	//aggiunge un servizio a un stanza
	@PostMapping("/admin/stanza/{stanza_id}/servizio")
	public String addServizio(@PathVariable("stanza_id") Long stanza_id, @Valid @ModelAttribute("servizio") Servizio servizio, BindingResult bindingResult, Model model) {
		this.servizioValidator.validate(servizio, bindingResult);

		if(!bindingResult.hasErrors()) {
			Stanza stanza = stanzaService.findById(stanza_id);
			stanza.addServizio(servizio);
			servizioService.save(servizio);
			model.addAttribute("stanza", stanza);
			return "admin/stanza.html";
		}
		model.addAttribute("stanza", stanzaService.findById(stanza_id));
		return "admin/servizioForm.html";
	}

	//visualizza tutti gli servizi nel sistema
	@GetMapping("/admin/servizi")
	public String getServizi(Model model) {
		List<Servizio> servizi = servizioService.findAll();
		model.addAttribute("servizi", servizi);
		return "admin/servizi.html";
	}

	//visualizza un servizio dal sistema
	@GetMapping("/admin/servizio/{servizio_id}")
	public String getServizio(@PathVariable("servizio_id") Long servizio_id, Model model) {
		Servizio servizio = servizioService.findById(servizio_id);
		model.addAttribute("servizio", servizio);
		return "admin/servizio.html";
	}

	//visualizza il form per l'inserimento di un servizio nel stanza
	@GetMapping("/admin/stanza/{stanza_id}/servizioForm")
	public String getServizioForm(@PathVariable("stanza_id") Long stanza_id, Model model) {
		model.addAttribute("servizio", new Servizio());
		model.addAttribute("stanza", stanzaService.findById(stanza_id));
		return "admin/servizioForm.html";
	}

	//visualizza la pagina di conferma rimozione di un servizio dal stanza
	@GetMapping("/admin/stanza/{stanza_id}/toDeleteServizio/{servizio_id}")
	public String toDeleteServizio(@PathVariable("stanza_id") Long stanza_id, @PathVariable("servizio_id") Long servizio_id, Model model) {
		model.addAttribute("servizio", servizioService.findById(servizio_id));
		model.addAttribute("stanza", stanzaService.findById(stanza_id));
		return "admin/confermaRimozioneServizio.html";
	}

	//elimina l'servizio dal stanza e dal sistema, ritorna alla pagina del stanza
	@GetMapping("/admin/stanza/{stanza_id}/deleteServizio/{servizio_id}")
	public String deleteServizio(@PathVariable("stanza_id") Long stanza_id, @PathVariable("servizio_id") Long servizio_id, Model model) {
		Stanza stanza = stanzaService.findById(stanza_id);
		Servizio servizio = servizioService.findById(servizio_id);
		stanza.removeServizio(servizio);
		servizioService.deleteById(servizio_id);
		return this.stanzaController.getStanza(stanza_id, model);
	}
	
	//USER
	
	//visualizza un servizio dal sistema
	@GetMapping("/servizio/{servizio_id}")
	public String getServizioUser(@PathVariable("servizio_id") Long servizio_id, Model model) {
		Servizio servizio = servizioService.findById(servizio_id);
		model.addAttribute("servizio", servizio);
		return "user/servizio.html";
	}
	
}
