package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	public boolean existsByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
	
	public Chef findByNomeAndCognomeAndNazionalita(String nome, String cognome, String nazionalita);
	
}
