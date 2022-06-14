package it.uniroma3.siw.esame.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.esame.model.Stanza;
import it.uniroma3.siw.esame.service.StanzaService;

@Component
public class StanzaValidator implements Validator{
	
	@Autowired
	private StanzaService stanzaService;
	
	@Override
	public void validate(Object o, Errors errors) {
		if(this.stanzaService.alreadyExists((Stanza)o)) {
			//errors.reject("stanza.duplicato");
		}
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Stanza.class.equals(aClass);
	}

}
