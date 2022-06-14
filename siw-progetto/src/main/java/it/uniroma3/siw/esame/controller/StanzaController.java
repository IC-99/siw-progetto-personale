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

import it.uniroma3.siw.esame.model.Hotel;
import it.uniroma3.siw.esame.model.Stanza;
import it.uniroma3.siw.esame.service.HotelService;
import it.uniroma3.siw.esame.service.StanzaService;
import it.uniroma3.siw.esame.validator.StanzaValidator;

@Controller
public class StanzaController {

	@Autowired 
	private StanzaService stanzaService;

	@Autowired
	private StanzaValidator stanzaValidator;

	@Autowired 
	private HotelService hotelService;
	
	@Autowired 
	private HotelController hotelController;
	
	//ADMIN

	//aggiunge una stanza a un hotel
	@PostMapping("/admin/hotel/{hotel_id}/stanza")
	public String addStanza(@PathVariable("hotel_id") Long hotel_id, @Valid @ModelAttribute("stanza") Stanza stanza, BindingResult bindingResult, Model model) {
		this.stanzaValidator.validate(stanza, bindingResult);

		if(!bindingResult.hasErrors()) {
			Hotel hotel = hotelService.findById(hotel_id);
			hotel.addStanza(stanza);
			stanzaService.save(stanza);
			model.addAttribute("hotel", hotel);
			return "admin/hotel.html";
		}
		model.addAttribute("hotel", hotelService.findById(hotel_id));
		return "admin/stanzaForm.html";
	}

	//visualizza tutte le stanze nel sistema
	@GetMapping("/admin/stanze")
	public String getStanze(Model model) {
		List<Stanza> stanze = stanzaService.findAll();
		model.addAttribute("stanze", stanze);
		return "admin/stanze.html";
	}

	//visualizza una stanza dal sistema
	@GetMapping("/admin/stanza/{stanza_id}")
	public String getStanza(@PathVariable("stanza_id") Long stanza_id, Model model) {
		Stanza stanza = stanzaService.findById(stanza_id);
		model.addAttribute("stanza", stanza);
		return "admin/stanza.html";
	}

	//visualizza il form per l'inserimento di una stanza nell'hotel
	@GetMapping("/admin/hotel/{hotel_id}/stanzaForm")
	public String getStanzaForm(@PathVariable("hotel_id") Long hotel_id, Model model) {
		model.addAttribute("stanza", new Stanza());
		model.addAttribute("hotel", hotelService.findById(hotel_id));
		return "admin/stanzaForm.html";
	}

	//visualizza la pagina di conferma rimozione di una stanza dall'hotel
	@GetMapping("/admin/hotel/{hotel_id}/toDeleteStanza/{stanza_id}")
	public String toDeleteStanza(@PathVariable("hotel_id") Long hotel_id, @PathVariable("stanza_id") Long stanza_id, Model model) {
		model.addAttribute("stanza", stanzaService.findById(stanza_id));
		model.addAttribute("hotel", hotelService.findById(hotel_id));
		return "admin/confermaRimozioneStanza.html";
	}

	//elimina una stanza dall'hotel e dal sistema, ritorna alla pagina dell'hotel
	@GetMapping("/admin/hotel/{hotel_id}/deleteStanza/{stanza_id}")
	public String deleteStanza(@PathVariable("hotel_id") Long hotel_id, @PathVariable("stanza_id") Long stanza_id, Model model) {
		Hotel hotel = hotelService.findById(hotel_id);
		Stanza stanza = stanzaService.findById(stanza_id);
		hotel.removeStanza(stanza);
		stanzaService.deleteById(stanza_id);
		return this.hotelController.getHotel(hotel_id, model);
	}

	//USER
	
	//visualizza una stanza dal sistema
	@GetMapping("/stanza/{stanza_id}")
	public String getStanzaUser(@PathVariable("stanza_id") Long stanza_id, Model model) {
		Stanza stanza = stanzaService.findById(stanza_id);
		model.addAttribute("stanza", stanza);
		return "user/stanza.html";
	}
}
