package it.uniroma3.siw.esame.validator;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.esame.model.Citta;
import it.uniroma3.siw.esame.service.CittaService;

@Component
public class CittaValidator implements Validator{

	@Autowired
	private CittaService cittaService;
	
	@Override
	public void validate(Object o, Errors errors) {
		if(this.cittaService.alreadyExists((Citta)o)) {
			errors.reject("citta.duplicato");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Citta.class.equals(aClass);
	}

}
