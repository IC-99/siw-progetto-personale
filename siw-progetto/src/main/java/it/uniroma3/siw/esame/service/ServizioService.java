package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.esame.model.Servizio;
import it.uniroma3.siw.esame.repository.ServizioRepository;

@Service
public class ServizioService {
	
	@Autowired
	private ServizioRepository servizioRepository;
	
	@Transactional //in scrittura
	public void save(Servizio servizio) {
		servizioRepository.save(servizio);
	}
	
	@Transactional
	public void deleteById(Long id) {
		servizioRepository.deleteById(id);
	}
	
	public Servizio findById(Long id) {
		return servizioRepository.findById(id).get();
	}
	
	public List<Servizio> findAll(){
		List<Servizio> ingredienti = new ArrayList<Servizio>();
		for(Servizio i : servizioRepository.findAll()) {
			ingredienti.add(i);
		}
		return ingredienti;
	}
	
	public boolean alreadyExists(Servizio servizio) {
		return this.servizioRepository.existsByNomeAndDescrizione(servizio.getNome(), servizio.getDescrizione());
	}
}
