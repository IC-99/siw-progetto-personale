package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Servizio;

public interface ServizioRepository extends CrudRepository<Servizio, Long> {

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);
}
