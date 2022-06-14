package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Stanza;

public interface StanzaRepository extends CrudRepository<Stanza, Long> {

	public boolean existsByNumero(Integer numero);
}
