package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.esame.model.Chef;
import it.uniroma3.siw.esame.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired
	private ChefRepository chefRepository;
	
	@Transactional //in scrittura
	public void save(Chef chef) {
		chefRepository.save(chef);
	}
	
	@Transactional
	public void deleteById(Long id) {
		chefRepository.deleteById(id);
	}
	
	public Chef findById(Long id) {
		return chefRepository.findById(id).get();
	}
	
	public List<Chef> findAll(){
		List<Chef> chefs = new ArrayList<Chef>();
		for(Chef c : chefRepository.findAll()) {
			chefs.add(c);
		}
		return chefs;
	}
	
	public boolean alreadyExists(Chef chef) {
		return this.chefRepository.existsByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita());
	}
	
	public Chef findByNomeCognomeNazionalita(Chef chef) {
		return this.chefRepository.findByNomeAndCognomeAndNazionalita(chef.getNome(), chef.getCognome(), chef.getNazionalita());
	}
	
}
