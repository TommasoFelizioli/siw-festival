package it.uniroma3.siw.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.dto.FestivalDto;
import it.uniroma3.siw.dto.FilmDto;
import it.uniroma3.siw.dto.ProiezioneDto;
import it.uniroma3.siw.dto.RecensioneDto;
import it.uniroma3.siw.model.Festival;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.model.Proiezione;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.service.FestivalService;
import it.uniroma3.siw.service.FilmService;
import it.uniroma3.siw.service.ProiezioneService;
import it.uniroma3.siw.service.RecensioneService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ApiController {

	private FestivalService festivalService;
	private FilmService filmService;
	private ProiezioneService proiezioneService;
	private RecensioneService recensioneService;

	public ApiController(FestivalService festivalService, FilmService filmService,
			ProiezioneService proiezioneService, RecensioneService recensioneService) {
		this.festivalService = festivalService;
		this.filmService = filmService;
		this.proiezioneService = proiezioneService;
		this.recensioneService = recensioneService;
	}

	@GetMapping("/festivals")
	public List<FestivalDto> getFestivals() {
		List<FestivalDto> risultato = new ArrayList<>();
		for (Festival festival : festivalService.findAll()) {
			risultato.add(toDto(festival));
		}
		return risultato;
	}

	@GetMapping("/festivals/{id}")
	public ResponseEntity<FestivalDto> getFestival(@PathVariable("id") Long id) {
		Festival festival = festivalService.findById(id);
		if (festival == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(toDto(festival));
	}

	@GetMapping("/festivals/{id}/movies")
	public ResponseEntity<List<FilmDto>> getFilmDelFestival(@PathVariable("id") Long id) {
		Festival festival = festivalService.findByIdConFilm(id);
		if (festival == null) {
			return ResponseEntity.notFound().build();
		}
		List<FilmDto> risultato = new ArrayList<>();
		for (Film film : festival.getFilms()) {
			risultato.add(toDto(film));
		}
		risultato.sort(Comparator.comparing(FilmDto::titolo));
		return ResponseEntity.ok(risultato);
	}

	@GetMapping("/festivals/{id}/screenings")
	public ResponseEntity<List<ProiezioneDto>> getProgramma(@PathVariable("id") Long id) {
		if (festivalService.findById(id) == null) {
			return ResponseEntity.notFound().build();
		}
		List<ProiezioneDto> risultato = new ArrayList<>();
		for (Proiezione proiezione : proiezioneService.findProgrammaByFestival(id)) {
			risultato.add(toDto(proiezione));
		}
		return ResponseEntity.ok(risultato);
	}

	@GetMapping("/movies/{id}")
	public ResponseEntity<FilmDto> getFilm(@PathVariable("id") Long id) {
		Film film = filmService.findByIdConRegistaEFestival(id);
		if (film == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(toDto(film));
	}

	@GetMapping("/movies/{id}/reviews")
	public ResponseEntity<List<RecensioneDto>> getRecensioni(@PathVariable("id") Long id) {
		if (filmService.findByIdConRegistaEFestival(id) == null) {
			return ResponseEntity.notFound().build();
		}
		List<RecensioneDto> risultato = new ArrayList<>();
		for (Recensione recensione : recensioneService.findByFilm(id)) {
			risultato.add(toDto(recensione));
		}
		return ResponseEntity.ok(risultato);
	}

	private FestivalDto toDto(Festival f) {
		return new FestivalDto(f.getId(), f.getNome(), f.getAnno(), f.getCitta(), f.getDataInizio(),
				f.getDataFine(), f.getDescrizione());
	}

	private FilmDto toDto(Film f) {
		String regista = null;
		if (f.getRegista() != null) {
			regista = f.getRegista().getNome() + " " + f.getRegista().getCognome();
		}
		return new FilmDto(f.getId(), f.getTitolo(), f.getAnno(), f.getDurata(), f.getGenere(),
				f.getPaeseProduzione(), regista);
	}

	private ProiezioneDto toDto(Proiezione p) {
		return new ProiezioneDto(p.getId(), p.getData(), p.getOra(), p.getOraFine(), p.getStato().name(),
				p.getFilm().getId(), p.getFilm().getTitolo(), p.getSala().getNome());
	}

	private RecensioneDto toDto(Recensione r) {
		String autore = r.getAutore().getName() + " " + r.getAutore().getSurname();
		return new RecensioneDto(r.getId(), autore, r.getVoto(), r.getTesto(), r.getData());
	}
}