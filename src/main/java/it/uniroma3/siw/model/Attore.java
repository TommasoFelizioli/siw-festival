package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

@Entity
public class Attore {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Campo obbligatorio")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "Campo obbligatorio")
	@Column(nullable = false)
	private String cognome;

	@Past(message = "La data deve essere nel passato")
	private LocalDate dataNascita;

	private String nazionalita;

	@OneToMany(mappedBy = "attore")
	private List<Interpretazione> interpretazioni = new ArrayList<>();

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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<Interpretazione> getInterpretazioni() {
		return interpretazioni;
	}

	public void setInterpretazioni(List<Interpretazione> interpretazioni) {
		this.interpretazioni = interpretazioni;
	}
}