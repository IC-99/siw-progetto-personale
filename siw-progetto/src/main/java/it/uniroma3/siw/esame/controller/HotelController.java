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
import it.uniroma3.siw.esame.validator.HotelValidator;

@Controller
public class HotelController {

	@Autowired 
	private HotelService hotelService;

	@Autowired
	private HotelValidator hotelValidator;

	@Autowired 
	private CittaService cittaService;
	
	//ADMIN

	//aggiunge un hotel
	@PostMapping("/admin/hotel")
	public String addHotel(@Valid @ModelAttribute("hotel") Hotel hotel, BindingResult bindingResult, Model model) {
		this.hotelValidator.validate(hotel, bindingResult);

		if(!bindingResult.hasErrors()) {
			hotelService.save(hotel);
			model.addAttribute("hotel", hotel);
			return "admin/hotel.html";
		}
		model.addAttribute("fromCitta", false);
		return "admin/hotelForm.html";
	}

	//aggiunge un hotel nella citta
	@PostMapping("/admin/citta/{citta_id}/hotel")
	public String addHotelFromCitta(@PathVariable("citta_id") Long citta_id, @Valid @ModelAttribute("hotel") Hotel hotel, BindingResult bindingResult, Model model) {
		this.hotelValidator.validate(hotel, bindingResult);

		if(!bindingResult.hasErrors()) {
			Citta citta = cittaService.findById(citta_id);
			citta.addHotel(hotel);
			hotel.setCitta(citta);
			hotelService.save(hotel);
			model.addAttribute("citta", citta);
			return "admin/citta.html";
		}
		model.addAttribute("citta", cittaService.findById(citta_id));
		model.addAttribute("fromCitta", true);
		return "admin/hotelForm.html";
	}

	//visualizza tutti gli hotel (non specifico id)
	@GetMapping("/admin/hotels")
	public String getHotels(Model model) {
		List<Hotel> hotels = hotelService.findAll();
		model.addAttribute("hotels", hotels);
		return "admin/hotels.html";
	}

	//visualizza un hotel
	@GetMapping("/admin/hotel/{id}")
	public String getHotel(@PathVariable("id") Long id, Model model) {
		Hotel hotel = hotelService.findById(id);
		model.addAttribute("hotel", hotel);
		return "admin/hotel.html";
	}

	//visualizza il form per l'inserimento di un hotel
	@GetMapping("/admin/hotelForm")
	public String getHotelForm(Model model) {
		model.addAttribute("hotel", new Hotel());
		model.addAttribute("fromCitta", false);
		return "admin/hotelForm.html";
	}

	//visualizza il form per l'inserimento di un hotel nella citta
	@GetMapping("/admin/citta/{citta_id}/hotelForm")
	public String getHotelForm(@PathVariable("citta_id") Long citta_id, Model model) {
		model.addAttribute("hotel", new Hotel());
		model.addAttribute("citta", cittaService.findById(citta_id));
		model.addAttribute("fromCitta", true);
		return "admin/hotelForm.html";
	}

	//visualizza la pagina di conferma rimozione di un hotel
	@GetMapping("/admin/toDeleteHotel/{id}")
	public String toDeleteHotel(@PathVariable("id") Long id, Model model) {
		model.addAttribute("hotel", hotelService.findById(id));
		return "admin/confermaRimozioneHotel.html";
	}

	//elimina l'hotel e ritorna alla lista di tutti gli hotel
	@GetMapping("/admin/deleteHotel/{id}")
	public String deleteHotel(@PathVariable("id") Long id, Model model) {
		hotelService.deleteById(id);
		return this.getHotels(model);
	}
	
	//USER
	
	//visualizza tutti gli hotel (non specifico id)
	@GetMapping("/hotels")
	public String getHotelsUser(Model model) {
		List<Hotel> hotels = hotelService.findAll();
		model.addAttribute("hotels", hotels);
		return "user/hotels.html";
	}
	
	//visualizza un hotel
		@GetMapping("/hotel/{id}")
		public String getHotelUser(@PathVariable("id") Long id, Model model) {
			Hotel hotel = hotelService.findById(id);
			model.addAttribute("hotel", hotel);
			return "user/hotel.html";
		}
}
