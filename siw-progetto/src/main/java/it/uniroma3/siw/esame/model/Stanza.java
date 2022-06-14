package it.uniroma3.siw.esame.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Stanza {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private Integer numero;
	
	@NotBlank
	private Float prezzoNotte;
	
	@NotBlank
	private String descrizione;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<Servizio> servizi;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Float getPrezzoNotte() {
		return prezzoNotte;
	}

	public void setPrezzoNotte(Float prezzoNotte) {
		this.prezzoNotte = prezzoNotte;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public List<Servizio> getServizi() {
		return servizi;
	}

	public void setServizi(List<Servizio> servizi) {
		this.servizi = servizi;
	}
	
	public void addServizio(Servizio servizio) {
		if(this.servizi == null) {
			this.servizi = new ArrayList<Servizio>();
		}
		this.servizi.add(servizio);
	}
	
	public void removeServizio(Servizio servizio) {
		this.servizi.remove(servizio);
	}
}
