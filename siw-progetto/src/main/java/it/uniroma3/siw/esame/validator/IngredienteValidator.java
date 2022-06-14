package it.uniroma3.siw.esame.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.esame.model.Ingrediente;
import it.uniroma3.siw.esame.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator{
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Override
	public void validate(Object o, Errors errors) {
		if(this.ingredienteService.alreadyExists((Ingrediente)o)) {
			//errors.reject("ingrediente.duplicato");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Ingrediente.class.equals(aClass);
	}

}
