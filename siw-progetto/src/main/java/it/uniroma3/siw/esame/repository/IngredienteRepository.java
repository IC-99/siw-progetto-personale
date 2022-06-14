package it.uniroma3.siw.esame.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.esame.model.Ingrediente;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long> {

	public boolean existsByNomeAndDescrizioneAndOrigine(String nome, String descrizione, String origine);
}
