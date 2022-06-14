package it.uniroma3.siw.esame.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.esame.model.Buffet;
import it.uniroma3.siw.esame.repository.BuffetRepository;

@Service
public class BuffetService {
	
	@Autowired
	private BuffetRepository buffetRepository;
	
	@Transactional //in scrittura
	public void save(Buffet buffet) {
		buffetRepository.save(buffet);
	}
	
	@Transactional
	public void deleteById(Long id) {
		buffetRepository.deleteById(id);
	}
	
	public Buffet findById(Long id) {
		return buffetRepository.findById(id).get();
	}
	
	public List<Buffet> findAll(){
		List<Buffet> buffets = new ArrayList<Buffet>();
		for(Buffet b : buffetRepository.findAll()) {
			buffets.add(b);
		}
		return buffets;
	}
	
	public boolean alreadyExists(Buffet buffet) {
		return this.buffetRepository.existsByNomeAndDescrizione(buffet.getNome(), buffet.getDescrizione());
	}
}