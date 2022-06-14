package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Buffet;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);
}
