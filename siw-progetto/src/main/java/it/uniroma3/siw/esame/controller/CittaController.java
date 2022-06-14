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
import it.uniroma3.siw.esame.model.Citta;
import it.uniroma3.siw.esame.service.HotelService;
import it.uniroma3.siw.esame.service.CittaService;
import it.uniroma3.siw.esame.validator.CittaValidator;

@Controller
public class CittaController {
	
	@Autowired 
	private CittaService cittaService;

	@Autowired
	private CittaValidator cittaValidator;
	
	@Autowired 
	private HotelService hotelService;
	
	//ADMIN
	
	//aggiunge un citta
	@PostMapping("/admin/citta")
	public String addCitta(@Valid @ModelAttribute("citta") Citta citta, BindingResult bindingResult, Model model) {
		this.cittaValidator.validate(citta, bindingResult);

		if(!bindingResult.hasErrors()) {
			cittaService.save(citta);
			model.addAttribute("citta", citta);
			return "admin/citta.html";
		}
		model.addAttribute("fromHotel", false);
		return "admin/cittaForm.html";
	}

	//aggiunge il citta a un hotel
	@PostMapping("/admin/hotel/{hotel_id}/citta")
	public String addCitta(@PathVariable("hotel_id") Long hotel_id, @Valid @ModelAttribute("citta") Citta citta, BindingResult bindingResult, Model model) {

		if(!bindingResult.hasErrors()) {
			Hotel hotel = hotelService.findById(hotel_id);
			
			if(cittaService.alreadyExists(citta)) {
				Citta cittaNelSistema = cittaService.findByNomeCognomeCitta(citta);
				hotel.setCitta(cittaNelSistema);
				cittaService.save(cittaNelSistema);
			}
			else {
				hotel.setCitta(citta);
				cittaService.save(citta);
			}
			model.addAttribute("hotel", hotel);
			return "admin/hotel.html";
		}
		model.addAttribute("hotel", hotelService.findById(hotel_id));
		model.addAttribute("fromHotel", true);
		return "admin/cittaForm.html";
	}

	//visualizza tutti i cittas nel sistema
	@GetMapping("/admin/cittas")
	public String getCittas(Model model) {
		List<Citta> cittas = cittaService.findAll();
		model.addAttribute("chefs", cittas);
		return "admin/cittas.html";
	}

	//visualizza un citta presente nel sistema
	@GetMapping("/admin/citta/{citta_id}")
	public String getChef(@PathVariable("citta_id") Long citta_id, Model model) {
		Citta citta = cittaService.findById(citta_id);
		model.addAttribute("citta", citta);
		return "admin/citta.html";
	}

	//visualizza il form per l'inserimento di un citta
	@GetMapping("/admin/cittaForm")
	public String getCittaForm(Model model) {
		model.addAttribute("citta", new Citta());
		model.addAttribute("fromHotel", false);
		return "admin/cittaForm.html";
	}
	
	//visualizza il form per l'inserimento di un citta di un hotel
	@GetMapping("/admin/hotel/{hotel_id}/cittaForm")
	public String getCittaForm(@PathVariable("hotel_id") Long hotel_id, Model model) {
		model.addAttribute("citta", new Citta());
		model.addAttribute("hotel", hotelService.findById(hotel_id));
		model.addAttribute("fromHotel", true);
		return "admin/cittaForm.html";
	}
	
	//visualizza la pagina di conferma rimozione del citta dal sistema
	@GetMapping("/admin/toDeleteCitta/{citta_id}")
	public String toDeleteCitta(@PathVariable("citta_id") Long citta_id, Model model) {
		model.addAttribute("citta", cittaService.findById(citta_id));
		return "admin/confermaRimozioneCitta.html";
	}

	//elimina il citta dall'hotel e dal sistema, ritorna alla pagina dell'hotel
	@GetMapping("/admin/deleteCitta/{citta_id}")
	public String deleteCitta(@PathVariable("citta_id") Long citta_id, Model model) {
		cittaService.deleteById(citta_id);
		return this.getCittas(model);
	}
	
	//USER
	
	//visualizza tutti i cittas nel sistema
	@GetMapping("/cittas")
	public String getCittasUser(Model model) {
		List<Citta> cittas = cittaService.findAll();
		model.addAttribute("cittas", cittas);
		return "user/cittas.html";
	}
	
	//visualizza un citta presente nel sistema
	@GetMapping("/citta/{citta_id}")
	public String getChefUser(@PathVariable("chef_id") Long chef_id, Model model) {
		Citta citta = cittaService.findById(chef_id);
		model.addAttribute("chef", citta);
		return "user/chef.html";
	}

}
