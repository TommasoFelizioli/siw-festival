package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.exception.FilmNonValidoException;
import it.uniroma3.siw.model.Attore;
import it.uniroma3.siw.model.Film;
import it.uniroma3.siw.model.Interpretazione;
import it.uniroma3.siw.model.Regista;
import it.uniroma3.siw.model.StatoProiezione;
import it.uniroma3.siw.repository.AttoreRepository;
import it.uniroma3.siw.repository.FilmRepository;
import it.uniroma3.siw.repository.InterpretazioneRepository;
import it.uniroma3.siw.repository.ProiezioneRepository;
import it.uniroma3.siw.repository.RegistaRepository;

@Service
public class FilmService {

	private FilmRepository filmRepository;
	private InterpretazioneRepository interpretazioneRepository;
	private RegistaRepository registaRepository;
	private AttoreRepository attoreRepository;
	private ProiezioneRepository proiezioneRepository;

	public FilmService(FilmRepository filmRepository, InterpretazioneRepository interpretazioneRepository,
			RegistaRepository registaRepository, AttoreRepository attoreRepository,
			ProiezioneRepository proiezioneRepository) {
		this.filmRepository = filmRepository;
		this.interpretazioneRepository = interpretazioneRepository;
		this.registaRepository = registaRepository;
		this.attoreRepository = attoreRepository;
		this.proiezioneRepository = proiezioneRepository;
	}

	@Transactional(readOnly = true)
	public List<Film> findAll() {
		return filmRepository.findAllConRegista();
	}

	@Transactional(readOnly = true)
	public Film findByIdConRegistaEFestival(Long id) {
		return filmRepository.findByIdConRegistaEFestival(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Interpretazione> findInterpretazioni(Long filmId) {
		return interpretazioneRepository.findByFilmConAttore(filmId);
	}

	@Transactional(readOnly = true)
	public List<Film> findNonPartecipanti(Long festivalId) {
		return filmRepository.findNonPartecipanti(festivalId);
	}

	@Transactional(readOnly = true)
	public List<Regista> findRegisti() {
		return registaRepository.findAllByOrderByCognomeAscNomeAsc();
	}

	@Transactional
	public Film save(Film film, Long registaId) {
		Regista regista = cercaRegista(registaId);
		if (filmRepository.existsByTitoloAndAnno(film.getTitolo(), film.getAnno())) {
			throw new FilmNonValidoException("Esiste già un film con lo stesso titolo e anno");
		}
		film.setRegista(regista);
		return filmRepository.save(film);
	}

	@Transactional
	public Film update(Long id, Film nuoviDati, Long registaId) {
		Film film = filmRepository.findById(id).orElse(null);
		if (film == null) {
			return null;
		}
		Regista regista = cercaRegista(registaId);

		boolean titoloOAnnoCambiati = !film.getTitolo().equals(nuoviDati.getTitolo())
				|| !film.getAnno().equals(nuoviDati.getAnno());
		if (titoloOAnnoCambiati && filmRepository.existsByTitoloAndAnno(nuoviDati.getTitolo(), nuoviDati.getAnno())) {
			throw new FilmNonValidoException("Esiste già un film con lo stesso titolo e anno");
		}

		boolean durataCambiata = !film.getDurata().equals(nuoviDati.getDurata());
		if (durataCambiata && proiezioneRepository.existsByFilmIdAndStato(id, StatoProiezione.SCHEDULED)) {
			throw new FilmNonValidoException(
					"Il film ha proiezioni programmate: non si può cambiare la durata finché non vengono cancellate");
		}

		film.setTitolo(nuoviDati.getTitolo());
		film.setAnno(nuoviDati.getAnno());
		film.setDurata(nuoviDati.getDurata());
		film.setGenere(nuoviDati.getGenere());
		film.setPaeseProduzione(nuoviDati.getPaeseProduzione());
		film.setRegista(regista);
		return film;
	}

	@Transactional
	public void aggiungiInterpretazione(Long filmId, Long attoreId, String personaggio) {
		Film film = filmRepository.findById(filmId).orElse(null);
		Attore attore = null;
		if (attoreId != null) {
			attore = attoreRepository.findById(attoreId).orElse(null);
		}
		if (film == null || attore == null) {
			throw new FilmNonValidoException("Scegli un attore");
		}
		if (personaggio == null || personaggio.isBlank()) {
			throw new FilmNonValidoException("Scrivi il nome del personaggio");
		}
		String nome = personaggio.trim();
		if (interpretazioneRepository.existsByFilmIdAndAttoreIdAndPersonaggio(filmId, attoreId, nome)) {
			throw new FilmNonValidoException("Questo attore interpreta già questo personaggio nel film");
		}

		Interpretazione interpretazione = new Interpretazione();
		interpretazione.setFilm(film);
		interpretazione.setAttore(attore);
		interpretazione.setPersonaggio(nome);
		interpretazioneRepository.save(interpretazione);
	}

	@Transactional
	public void rimuoviInterpretazione(Long interpretazioneId) {
		interpretazioneRepository.deleteById(interpretazioneId);
	}

	private Regista cercaRegista(Long registaId) {
		Regista regista = null;
		if (registaId != null) {
			regista = registaRepository.findById(registaId).orElse(null);
		}
		if (regista == null) {
			throw new FilmNonValidoException("Scegli un regista");
		}
		return regista;
	}
}