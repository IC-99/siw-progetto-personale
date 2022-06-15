package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Hotel;

public interface HotelRepository extends CrudRepository<Hotel, Long> {

	public boolean existsByNomeAndDescrizioneAndStelle(String nome, String descrizione, Integer stelle);
}
