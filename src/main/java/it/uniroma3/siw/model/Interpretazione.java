package it.uniroma3.siw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "film_id", "attore_id", "personaggio" }))
public class Interpretazione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Campo obbligatorio")
	@Column(nullable = false)
	private String personaggio;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "film_id")
	private Film film;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "attore_id")
	private Attore attore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonaggio() {
		return personaggio;
	}

	public void setPersonaggio(String personaggio) {
		this.personaggio = personaggio;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Attore getAttore() {
		return attore;
	}

	public void setAttore(Attore attore) {
		this.attore = attore;
	}
}