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

import it.uniroma3.siw.esame.model.Ingrediente;
import it.uniroma3.siw.esame.model.Piatto;
import it.uniroma3.siw.esame.service.IngredienteService;
import it.uniroma3.siw.esame.service.PiattoService;
import it.uniroma3.siw.esame.validator.IngredienteValidator;

@Controller
public class IngredienteController {

	@Autowired 
	private IngredienteService ingredienteService;

	@Autowired
	private IngredienteValidator ingredienteValidator;

	@Autowired 
	private PiattoService piattoService;

	@Autowired
	private PiattoController piattoController;

	//ADMIN
	
	//aggiunge un ingrediente a un piatto
	@PostMapping("/admin/piatto/{piatto_id}/ingrediente")
	public String addIngrediente(@PathVariable("piatto_id") Long piatto_id, @Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResult, Model model) {
		this.ingredienteValidator.validate(ingrediente, bindingResult);

		if(!bindingResult.hasErrors()) {
			Piatto piatto = piattoService.findById(piatto_id);
			piatto.addIngrediente(ingrediente);
			ingredienteService.save(ingrediente);
			model.addAttribute("piatto", piatto);
			return "admin/piatto.html";
		}
		model.addAttribute("piatto", piattoService.findById(piatto_id));
		return "admin/ingredienteForm.html";
	}

	//visualizza tutti gli ingredienti nel sistema
	@GetMapping("/admin/ingredienti")
	public String getIngredienti(Model model) {
		List<Ingrediente> ingredienti = ingredienteService.findAll();
		model.addAttribute("ingredienti", ingredienti);
		return "admin/ingredienti.html";
	}

	//visualizza un ingrediente dal sistema
	@GetMapping("/admin/ingrediente/{ingrediente_id}")
	public String getIngrediente(@PathVariable("ingrediente_id") Long ingrediente_id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(ingrediente_id);
		model.addAttribute("ingrediente", ingrediente);
		return "admin/ingrediente.html";
	}

	//visualizza il form per l'inserimento di un ingrediente nel piatto
	@GetMapping("/admin/piatto/{piatto_id}/ingredienteForm")
	public String getIngredienteForm(@PathVariable("piatto_id") Long piatto_id, Model model) {
		model.addAttribute("ingrediente", new Ingrediente());
		model.addAttribute("piatto", piattoService.findById(piatto_id));
		return "admin/ingredienteForm.html";
	}

	//visualizza la pagina di conferma rimozione di un ingrediente dal piatto
	@GetMapping("/admin/piatto/{piatto_id}/toDeleteIngrediente/{ingrediente_id}")
	public String toDeleteIngrediente(@PathVariable("piatto_id") Long piatto_id, @PathVariable("ingrediente_id") Long ingrediente_id, Model model) {
		model.addAttribute("ingrediente", ingredienteService.findById(ingrediente_id));
		model.addAttribute("piatto", piattoService.findById(piatto_id));
		return "admin/confermaRimozioneIngrediente.html";
	}

	//elimina l'ingrediente dal piatto e dal sistema, ritorna alla pagina del piatto
	@GetMapping("/admin/piatto/{piatto_id}/deleteIngrediente/{ingrediente_id}")
	public String deleteIngrediente(@PathVariable("piatto_id") Long piatto_id, @PathVariable("ingrediente_id") Long ingrediente_id, Model model) {
		Piatto piatto = piattoService.findById(piatto_id);
		Ingrediente ingrediente = ingredienteService.findById(ingrediente_id);
		piatto.removeIngrediente(ingrediente);
		ingredienteService.deleteById(ingrediente_id);
		return this.piattoController.getPiatto(piatto_id, model);
	}
	
	//USER
	
	//visualizza un ingrediente dal sistema
	@GetMapping("/ingrediente/{ingrediente_id}")
	public String getIngredienteUser(@PathVariable("ingrediente_id") Long ingrediente_id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(ingrediente_id);
		model.addAttribute("ingrediente", ingrediente);
		return "user/ingrediente.html";
	}
	
}
