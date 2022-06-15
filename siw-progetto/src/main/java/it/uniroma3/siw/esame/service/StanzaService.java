package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.esame.model.Stanza;
import it.uniroma3.siw.esame.repository.StanzaRepository;

@Service
public class StanzaService {
	
	@Autowired
	private StanzaRepository stanzaRepository;
	
	@Transactional //in scrittura
	public void save(Stanza stanza) {
		stanzaRepository.save(stanza);
	}
	
	@Transactional
	public void deleteById(Long id) {
		stanzaRepository.deleteById(id);
	}
	
	public Stanza findById(Long id) {
		return stanzaRepository.findById(id).get();
	}
	
	public List<Stanza> findAll(){
		List<Stanza> stanze = new ArrayList<Stanza>();
		for(Stanza s : stanzaRepository.findAll()) {
			stanze.add(s);
		}
		return stanze;
	}
	
	public boolean alreadyExists(Stanza stanza) {
		return this.stanzaRepository.existsByNumero(stanza.getNumero());
	}
}