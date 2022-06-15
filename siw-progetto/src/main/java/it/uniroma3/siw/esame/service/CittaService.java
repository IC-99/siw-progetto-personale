package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.esame.model.Citta;
import it.uniroma3.siw.esame.repository.CittaRepository;

@Service
public class CittaService {

	@Autowired
	private CittaRepository cittaRepository;
	
	@Transactional //in scrittura
	public void save(Citta citta) {
		cittaRepository.save(citta);
	}
	
	@Transactional
	public void deleteById(Long id) {
		cittaRepository.deleteById(id);
	}
	
	public Citta findById(Long id) {
		return cittaRepository.findById(id).get();
	}
	
	public List<Citta> findAll(){
		List<Citta> proprietari = new ArrayList<Citta>();
		for(Citta c : cittaRepository.findAll()) {
			proprietari.add(c);
		}
		return proprietari;
	}
	
	public boolean alreadyExists(Citta citta) {
		return this.cittaRepository.existsByNomeAndRegione(citta.getNome(), citta.getRegione());
	}
	
	public Citta findByNomeCognomeCitta(Citta citta) {
		return this.cittaRepository.findByNomeAndRegione(citta.getNome(), citta.getRegione());
	}
	
}
