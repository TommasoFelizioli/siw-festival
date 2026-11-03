package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "titolo", "anno" }))
public class Film {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Campo obbligatorio")
	@Column(nullable = false)
	private String titolo;

	@NotNull(message = "Campo obbligatorio")
	@Column(nullable = false)
	private Integer anno;

	@NotNull(message = "Campo obbligatorio")
	@Min(value = 1, message = "La durata deve essere di almeno 1 minuto")
	private Integer durata;

	@NotBlank(message = "Campo obbligatorio")
	private String genere;

	private String paeseProduzione;

	@ManyToOne(fetch = FetchType.LAZY)
	private Regista regista;

	@OneToMany(mappedBy = "film")
	private List<Interpretazione> interpretazioni = new ArrayList<>();

	@ManyToMany(mappedBy = "films")
	private Set<Festival> festivals = new HashSet<>();

	@OneToMany(mappedBy = "film")
	private List<Proiezione> proiezioni = new ArrayList<>();
	
	@OneToMany(mappedBy = "film")
	private List<Recensione> recensioni = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Integer getDurata() {
		return durata;
	}

	public void setDurata(Integer durata) {
		this.durata = durata;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public String getPaeseProduzione() {
		return paeseProduzione;
	}

	public void setPaeseProduzione(String paeseProduzione) {
		this.paeseProduzione = paeseProduzione;
	}

	public Regista getRegista() {
		return regista;
	}

	public void setRegista(Regista regista) {
		this.regista = regista;
	}

	public List<Interpretazione> getInterpretazioni() {
		return interpretazioni;
	}

	public void setInterpretazioni(List<Interpretazione> interpretazioni) {
		this.interpretazioni = interpretazioni;
	}

	public Set<Festival> getFestivals() {
		return festivals;
	}

	public void setFestivals(Set<Festival> festivals) {
		this.festivals = festivals;
	}

	public List<Proiezione> getProiezioni() {
		return proiezioni;
	}

	public void setProiezioni(List<Proiezione> proiezioni) {
		this.proiezioni = proiezioni;
	}
	
	public List<Recensione> getRecensioni() {
		return recensioni;
	}

	public void setRecensioni(List<Recensione> recensioni) {
		this.recensioni = recensioni;
	}
}