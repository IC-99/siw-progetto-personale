package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Citta;

public interface CittaRepository extends CrudRepository<Citta, Long> {

	public boolean existsByNomeAndRegione(String nome, String regione);
	
	public Citta findByNomeAndRegione(String nome, String regione);
	
}
