package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {

	public boolean existsByNomeAndDescrizione(String nome, String descrizione);
}
