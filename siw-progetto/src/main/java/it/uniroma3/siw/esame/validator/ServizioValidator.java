package it.uniroma3.siw.esame.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.esame.model.Servizio;
import it.uniroma3.siw.esame.service.ServizioService;

@Component
public class ServizioValidator implements Validator{
	
	@Autowired
	private ServizioService servizioService;
	
	@Override
	public void validate(Object o, Errors errors) {
		if(this.servizioService.alreadyExists((Servizio)o)) {
			//errors.reject("ingrediente.duplicato");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Servizio.class.equals(aClass);
	}

}
