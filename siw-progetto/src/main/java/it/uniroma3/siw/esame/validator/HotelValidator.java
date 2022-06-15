package it.uniroma3.siw.esame.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.esame.model.Hotel;
import it.uniroma3.siw.esame.model.User;
import it.uniroma3.siw.esame.service.HotelService;



@Component
public class HotelValidator implements Validator{
	
	@Autowired
	private HotelService hotelService;
	
	@Override
	public void validate(Object o, Errors errors) {
		
		Hotel hotel = (Hotel) o;
		
		if(this.hotelService.alreadyExists(hotel)) {
			errors.reject("hotel.duplicato");
		}
		
        Integer stelle = hotel.getStelle();

        if(stelle != null) {
        	if (stelle < 1 || stelle > 5) {
        		errors.rejectValue("stelle", "size");
        	}
        }
		
	}
	
	@Override
	public boolean supports(Class<?> aClass) {
		return Hotel.class.equals(aClass);
	}

}
