package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Proiezione {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private LocalDate data;

	@Column(nullable = false)
	private LocalTime ora;

	@Column(nullable = false)
	private LocalTime oraFine;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private StatoProiezione stato = StatoProiezione.SCHEDULED;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Festival festival;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Film film;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Sala sala;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getOra() {
		return ora;
	}

	public void setOra(LocalTime ora) {
		this.ora = ora;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public StatoProiezione getStato() {
		return stato;
	}

	public void setStato(StatoProiezione stato) {
		this.stato = stato;
	}

	public Festival getFestival() {
		return festival;
	}

	public void setFestival(Festival festival) {
		this.festival = festival;
	}

	public Film getFilm() {
		return film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}
}